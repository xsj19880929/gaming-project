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
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class InfoCrawlerService {
    private final Logger logger = LoggerFactory.getLogger(InfoCrawlerService.class);
    public List<JSONObject> taskList = Collections.synchronizedList(new ArrayList<JSONObject>());
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

    public void startTread(int threadNum) {
        // 生成任务
        List<CrCrawlTask> crCrawlTaskList = crCrawlTaskService.list("info");
        for (CrCrawlTask crCrawlTask : crCrawlTaskList) {
            taskList.add(JSONObject.fromObject(crCrawlTask));
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
//        CrCrawlTask crCrawlTask = new CrCrawlTask();
//        crCrawlTask.setTempleName("15wlist");
//        crCrawlTask.setUrl("http://www.15w.com/xinwen/");
//        JSONObject task1 = JSONObject.fromObject(crCrawlTask);
//        task1.put("infoZoneType", "trade");
//        task1.put("infoType", "news");
//        taskList.add(task1);
        while (taskList.size() > 0) {
            logger.info("剩余任务数 {} ", taskList.size());
            JSONObject task = taskList.remove(0);
            logger.info("当前任务： {} ", task.toString());
            try {
                HashMap<String, List<HashMap<String, String>>> results = crawlerBase.crawler(task, taskList);
                System.out.println(results);
                if (!results.isEmpty()) {
                    mergeData(results);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void mergeData(HashMap<String, List<HashMap<String, String>>> results) {
        List<HashMap<String, String>> infoList = results.get("info");
        List<HashMap<String, String>> tagList = results.get("tags");
        if (infoList != null) {
            HashMap<String, String> infoMap = infoList.get(0);
            Info info = setInfo(infoMap);
//            if (!dataOperatingService.selectData("uuid", infoMap.get("uuid"), "info")) {
//                dataOperatingService.insertDate(infoMap, "info");
//            }
            Info infoSelect = infoService.findByUuid(infoMap.get("uuid"));
            if (tagList != null && infoSelect == null) {
                infoService.save(info);
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

    }

    private Info setInfo(HashMap<String, String> infoMap) {
        Info info = new Info();
        info.setStatus(1);
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        info.setUuid(infoMap.get("uuid"));
        if (StringUtils.hasText(infoMap.get("zoneUuid"))) {
            info.setZoneUuid(infoMap.get("zoneUuid"));
            MatchZone matchZone = matchZoneService.findByUuid(infoMap.get("zoneUuid"));
            info.setZoneId(matchZone.getId());
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
        info.setVerify(1);
        info.setSource(infoMap.get("source"));
        info.setPublishTime(CalendarUtils.parse(infoMap.get("publishTime"), "yyyy-MM-dd HH:mm:ss"));
        info.setAuthor(infoMap.get("author"));
        info.setSeoKeywords(infoMap.get("seoKeywords"));
        info.setSeoDescription(infoMap.get("seoDescription"));
        return info;
    }

}
