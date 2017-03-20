package com.ygccw.crawler.schedule.service;


import com.ygccw.crawler.common.CalendarUtils;
import com.ygccw.crawler.common.CrawlerBase;
import com.ygccw.crawler.common.IKFunction;
import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;
import com.ygccw.wechat.common.crawler.service.CrCrawlTaskService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.entity.PictureDetail;
import com.ygccw.wechat.common.picture.enums.PictureZoneType;
import com.ygccw.wechat.common.picture.service.PictureDetailService;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import core.framework.util.JSONBinder;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ImageCrawlerService {
    private static final BlockingQueue<JSONObject> TASK_LIST = new LinkedBlockingQueue<>();
    private static final ConcurrentHashMap<String, Boolean> TASK_LAST = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(ImageCrawlerService.class);
    @Inject
    private CrawlerBase crawlerBase;
    @Inject
    private TagsService tagsService;
    @Inject
    private TagMappingService tagMappingService;
    @Inject
    private PictureService pictureService;
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private CrCrawlTaskService crCrawlTaskService;
    @Inject
    private PictureDetailService pictureDetailService;

    public void startTread(int threadNum) {
        // 生成任务
        List<CrCrawlTask> crCrawlTaskList = crCrawlTaskService.list("image");
        TASK_LAST.clear();
        for (CrCrawlTask crCrawlTask : crCrawlTaskList) {
            try {
                TASK_LIST.put(JSONObject.fromObject(crCrawlTask));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    startWork();
                }
            });
        }
        logger.info("启动任务数=======" + threadNum);
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    private void startWork() {
        while (TASK_LIST.size() > 0) {
            logger.info("剩余任务数 {} ", TASK_LIST.size());
            try {
                JSONObject task = TASK_LIST.take();
                logger.info("当前任务： {} ", task.toString());
                HashMap<String, List<HashMap<String, String>>> results = crawlerBase.crawler(task, TASK_LIST);
                logger.info("解析数据:" + JSONBinder.toJSON(results));
                if (!results.isEmpty()) {
                    mergeData(results, TASK_LAST);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }


        }
    }


    @Transactional
    private void mergeData(HashMap<String, List<HashMap<String, String>>> results, ConcurrentHashMap<String, Boolean> taskLast) {
        List<HashMap<String, String>> pictureList = results.get("picture");
        List<HashMap<String, String>> pictureDetailList = results.get("pictureDetail");
        List<HashMap<String, String>> tagsList = results.get("tags");
        if (pictureList == null) {
            return;
        }
        HashMap<String, String> pictureMap = pictureList.get(0);
        Picture picture = setPicture(pictureMap);
        Picture pictureSelect = pictureService.findByUuid(pictureMap.get("uuid"));
        if (pictureDetailList != null && pictureSelect == null) {
            updateTask(pictureMap, taskLast);
            pictureService.saveOnly(picture);
            saveTag(tagsList, picture);
            savePictureDetail(pictureDetailList, picture);
        }


    }

    private Picture setPicture(HashMap<String, String> pictureMap) {
        Picture picture = new Picture();
        picture.setStatus(1);
        picture.setCreateTime(new Date());
        picture.setUpdateTime(CalendarUtils.parse(pictureMap.get("publishTime"), "yyyy-MM-dd HH:mm:ss"));
        picture.setUuid(pictureMap.get("uuid"));
        if (StringUtils.hasText(pictureMap.get("zoneUuid"))) {
            picture.setZoneUuid(pictureMap.get("zoneUuid"));
            MatchZone matchZone = matchZoneService.findByUuid(pictureMap.get("zoneUuid"));
            picture.setZoneId(matchZone.getId());
        }
        picture.setPictureZoneType(PictureZoneType.valueOf(pictureMap.get("pictureZoneType")));
        picture.setImage(pictureMap.get("image"));
        picture.setDescription(pictureMap.get("description"));
        picture.setVisitCount(1);
        picture.setSeoTitle(pictureMap.get("seoTitle"));
        picture.setTags(pictureMap.get("tags"));
        picture.setVerify(0);
        picture.setSource(pictureMap.get("source"));
        picture.setPublishTime(CalendarUtils.parse(pictureMap.get("publishTime"), "yyyy-MM-dd HH:mm:ss"));
        picture.setSeoKeywords(pictureMap.get("seoKeywords"));
        picture.setSeoDescription(pictureMap.get("seoDescription"));
        picture.setWebSite(pictureMap.get("webSite"));
        return picture;
    }

    private void saveTag(List<HashMap<String, String>> tagsList, Picture picture) {
        if (tagsList != null) {
            for (HashMap<String, String> tagMap : tagsList) {
                Tags tags = new Tags();
                tags.setStatus(1);
                tags.setName(tagMap.get("name"));
                tags.setTagType(TagType.valueOf(tagMap.get("tagType")));
                tags.setTagZoneType(TagZoneType.valueOf(tagMap.get("tagZoneType")));
                tags.setUuid(tagMap.get("uuid"));
                Tags tagsSelect = tagsService.findByName(tags.getName(), tags.getTagType(), tags.getTagZoneType());
                if (tagsSelect != null) {
                    BeanUtils.copyProperties(tagsSelect, tags);
                } else {
                    tagsService.save(tags);
                }
                TagMapping tagMapping = new TagMapping();
                tagMapping.setCreateTime(new Date());
                tagMapping.setUpdateTime(new Date());
                tagMapping.setTagType(tags.getTagType());
                tagMapping.setTagZoneType(tags.getTagZoneType());
                tagMapping.setEntityId(picture.getId());
                tagMapping.setStatus(1);
                tagMapping.setTagsUuid(tags.getUuid());
                tagMapping.setTagsId(tags.getId());
                tagMapping.setUuid(IKFunction.uuid());
                tagMapping.setEntityUuid(picture.getUuid());
                tagMappingService.save(tagMapping);
            }
        }
    }

    private void savePictureDetail(List<HashMap<String, String>> pictureDetailList, Picture picture) {
        for (HashMap<String, String> pictureDetailMap : pictureDetailList) {
            PictureDetail pictureDetail = new PictureDetail();
            pictureDetail.setUuid(pictureDetailMap.get("uuid"));
            pictureDetail.setDescription(pictureDetailMap.get("description"));
            pictureDetail.setImage(pictureDetailMap.get("image"));
            pictureDetail.setStatus(1);
            pictureDetail.setPictureId(picture.getId());
            pictureDetail.setPictureUuid(picture.getUuid());
            pictureDetail.setSort(Integer.parseInt(pictureDetailMap.get("sort")));
            pictureDetailService.save(pictureDetail);
        }
    }

    private void updateTask(HashMap<String, String> infoMap, ConcurrentHashMap<String, Boolean> taskLast) {
        if (infoMap.get("taskId") != null) {
            if (taskLast.get(infoMap.get("taskId")) == null || !taskLast.get(infoMap.get("taskId"))) {
                CrCrawlTask crCrawlTask = crCrawlTaskService.findById(Long.parseLong(infoMap.get("taskId")));
                crCrawlTask.setLastUrl(infoMap.get("url").toString());
                crCrawlTaskService.update(crCrawlTask);
                taskLast.put(infoMap.get("taskId"), true);
            }
        }
    }

}
