package com.ygccw.crawler.schedule.service;


import com.ygccw.crawler.common.CalendarUtils;
import com.ygccw.crawler.common.CrawlerBase;
import com.ygccw.crawler.common.IKFunction;
import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;
import com.ygccw.wechat.common.crawler.service.CrCrawlTaskService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import core.framework.util.JSONBinder;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
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
public class InfoCrawlerService {
    private static final BlockingQueue<JSONObject> TASK_LIST = new LinkedBlockingQueue<>();
    private static final ConcurrentHashMap<String, Boolean> LAST_TASK = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(InfoCrawlerService.class);
    @Inject
    private CrawlerBase crawlerBase;
    @Inject
    private TagsService tagsService;
    @Inject
    private TagMappingService tagMappingService;
    @Inject
    private InfoService infoService;
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private CrCrawlTaskService crCrawlTaskService;
    @Inject
    private AnchorZoneService anchorZoneService;

    public void startTread(int threadNum) {
        // 生成任务
        List<CrCrawlTask> crCrawlTaskList = crCrawlTaskService.list("info");
        LAST_TASK.clear();
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
                    mergeData(results, LAST_TASK);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void mergeData(HashMap<String, List<HashMap<String, String>>> results, ConcurrentHashMap<String, Boolean> taskLast) {
        List<HashMap<String, String>> infoList = results.get("info");
        List<HashMap<String, String>> tagList = results.get("tags");
        if (infoList == null) {
            return;
        }
        HashMap<String, String> infoMap = infoList.get(0);
        Info info = setInfo(infoMap);
//            if (!dataOperatingService.selectData("uuid", infoMap.get("uuid"), "info")) {
//                dataOperatingService.insertDate(infoMap, "info");
//            }
        Info infoSelect = infoService.findByUuid(infoMap.get("uuid"));
        if (infoSelect == null) {
            updateTask(infoMap, taskLast);
            infoService.saveOnly(info);
            setTag(tagList, info);
        }

    }

    private void setTag(List<HashMap<String, String>> tagList, Info info) {
        if (tagList != null) {
            for (HashMap<String, String> tagMap : tagList) {
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
                tagMapping.setEntityId(info.getId());
                tagMapping.setStatus(1);
                tagMapping.setTagsUuid(tags.getUuid());
                tagMapping.setTagsId(tags.getId());
                tagMapping.setUuid(IKFunction.uuid());
                tagMapping.setEntityUuid(info.getUuid());
                tagMappingService.save(tagMapping);
            }
        }
    }

    private Info setInfo(HashMap<String, String> infoMap) {
        Info info = new Info();
        info.setStatus(1);
        info.setCreateTime(new Date());
        info.setUpdateTime(CalendarUtils.parse(infoMap.get("updateTime"), "yyyy-MM-dd HH:mm:ss"));
        info.setUuid(infoMap.get("uuid"));
        if (StringUtils.hasText(infoMap.get("zoneUuid"))) {
            info.setZoneUuid(infoMap.get("zoneUuid"));
            if (InfoZoneType.matchZone == InfoZoneType.valueOf(infoMap.get("infoZoneType"))) {
                MatchZone matchZone = matchZoneService.findByUuid(infoMap.get("zoneUuid"));
                info.setZoneId(matchZone.getId());
            } else if (InfoZoneType.anchorZone == InfoZoneType.valueOf(infoMap.get("infoZoneType"))) {
                AnchorZone anchorZone = anchorZoneService.findByUuId(infoMap.get("zoneUuid"));
                info.setZoneId(anchorZone.getId());
            }
        }
        info.setInfoZoneType(InfoZoneType.valueOf(infoMap.get("infoZoneType")));
        info.setInfoType(InfoType.valueOf(infoMap.get("infoType")));
        if (StringUtils.hasText(infoMap.get("infoVideoType"))) {
            info.setInfoVideoType(InfoVideoType.valueOf(infoMap.get("infoVideoType")));
        }
        info.setTitle(infoMap.get("title"));
        info.setSubTitle(infoMap.get("subTitle"));
        info.setVisitCount(1);
        info.setTitleImage(infoMap.get("titleImage"));
        info.setContent(infoMap.get("content"));
        info.setSeoTitle(infoMap.get("seoTitle"));
        info.setTags(infoMap.get("tags"));
        info.setVerify(0);
        info.setSource(infoMap.get("source"));
        info.setPublishTime(CalendarUtils.parse(infoMap.get("publishTime"), "yyyy-MM-dd HH:mm:ss"));
        info.setAuthor(infoMap.get("author"));
        info.setSeoKeywords(infoMap.get("seoKeywords"));
        info.setSeoDescription(infoMap.get("seoDescription"));
        return info;
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
