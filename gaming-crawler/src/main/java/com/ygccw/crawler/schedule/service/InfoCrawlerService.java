package com.ygccw.crawler.schedule.service;


import com.ygccw.crawler.common.CalendarUtils;
import com.ygccw.crawler.common.Constants;
import com.ygccw.crawler.common.CrawlerBase;
import com.ygccw.crawler.common.IKFunction;
import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;
import com.ygccw.wechat.common.crawler.service.CrCrawlTaskService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.entity.InfoContent;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoContentService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class InfoCrawlerService {
    //    private static final BlockingQueue<JSONObject> TASK_LIST = new LinkedBlockingQueue<>();
//    private static final ConcurrentHashMap<String, Boolean> LAST_TASK = new ConcurrentHashMap<>();
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
    @Inject
    private InfoContentService infoContentService;

    public void startTread(int threadNum, String type) {
        final BlockingQueue<JSONObject> TASK_LIST = new LinkedBlockingQueue<>();
        final ConcurrentHashMap<String, Boolean> LAST_TASK = new ConcurrentHashMap<>();
        //uuid写入内存
        setUUidMem();
        // 生成任务
        List<CrCrawlTask> crCrawlTaskList = crCrawlTaskService.list(type);
        logger.info("列表任务数量{}", LAST_TASK.size());
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
                    startWork(TASK_LIST, LAST_TASK);
                }
            });
        }
        logger.info("启动任务数=======" + threadNum);
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    private void startWork(BlockingQueue<JSONObject> TASK_LIST, ConcurrentHashMap<String, Boolean> LAST_TASK) {
        while (true) {
            logger.info("剩余任务数 {} ", TASK_LIST.size());
            try {
                JSONObject task = TASK_LIST.poll(50, TimeUnit.SECONDS);//没有数据等待最长50秒
                if (task != null) {
                    logger.info("当前任务： {} ", task.toString());
                    HashMap<String, List<HashMap<String, String>>> results = crawlerBase.crawler(task, TASK_LIST);
                    logger.info("解析数据:" + JSONBinder.toJSON(results));
                    if (!results.isEmpty()) {
                        mergeData(results, LAST_TASK);
                    }
                }
                if (TASK_LIST.size() == 0) {
                    logger.info("========{}任务已经跑完了", Thread.currentThread().getName());
                    break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void mergeData(HashMap<String, List<HashMap<String, String>>> results, ConcurrentHashMap<String, Boolean> taskLast) {
        List<HashMap<String, String>> infoList = results.get("info");
        List<HashMap<String, String>> tagList = results.get("tags");
        List<HashMap<String, String>> infoContentAddList = results.get("infoContent_add");
        addInfoContent(infoContentAddList);
        if (infoList == null) {
            return;
        }
        HashMap<String, String> infoMap = infoList.get(0);
//            if (!dataOperatingService.selectData("uuid", infoMap.get("uuid"), "info")) {
//                dataOperatingService.insertDate(infoMap, "info");
//            }
//        Info infoSelect = infoService.findByUuid(infoMap.get("uuid"));

        if (!Constants.INFO_UUID_SET.contains(infoMap.get("uuid"))) {
            Info info = setInfo(infoMap);
            if (StringUtils.hasText(info.getTitleImage())) { //页面展现需要头图
                logger.info("新增资讯内容UUID{}", info.getUuid());
                Constants.INFO_UUID_SET.add(info.getUuid());//增加一条咨询加入静态变量中
                updateTask(infoMap, taskLast);
                infoService.saveOnly(info);

                List<Tags> tagListData = setTag(tagList, info);
                if (!tagListData.isEmpty()) {
                    info.setContent(setContentTag(info.getContent(), tagListData, info.getInfoType().getName()));
                    infoService.update(info);
                }
            }

        }

    }

    private List<Tags> setTag(List<HashMap<String, String>> tagList, Info info) {
        List<Tags> tagsList = new ArrayList<>();
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
                tagsList.add(tags);
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
        return tagsList;
    }

    private Info setInfo(HashMap<String, String> infoMap) {

        IKFunction ikFunction = new IKFunction();
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
        info.setTitleImage(ikFunction.url2LocalImage(infoMap.get("titleImage"))); //需要保存的内容才下载图片
        info.setContent(ikFunction.imageTagDownLoad(infoMap.get("content"))); //需要保存的内容才下载图片
        info.setSeoTitle(infoMap.get("seoTitle"));
        info.setTags(infoMap.get("tags"));
        info.setVerify(Integer.parseInt(infoMap.get("verify")));
        info.setSource(infoMap.get("source"));
        info.setPublishTime(CalendarUtils.parse(infoMap.get("publishTime"), "yyyy-MM-dd HH:mm:ss"));
        info.setAuthor(infoMap.get("author"));
        info.setSeoKeywords(infoMap.get("seoKeywords"));
        info.setSeoDescription(infoMap.get("seoDescription"));
        info.setWebSite(infoMap.get("webSite"));
        info.setIfAutoPublish(0);
        if (StringUtils.hasText(infoMap.get("taskId"))) {
            info.setTaskId(Long.parseLong(infoMap.get("taskId")));
        }
        info.setSourceUrl(infoMap.get("url"));
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

    private void addInfoContent(List<HashMap<String, String>> infoContentAddList) {
        if (infoContentAddList != null) {
            HashMap<String, String> infoMap = infoContentAddList.get(0);
            InfoContent infoContent = infoContentService.findByUuid(infoMap.get("uuid"));
            if (infoContent != null && !infoContent.getContent().contains(infoMap.get("content").trim())) {
                infoContent.setContent(infoContent.getContent() + infoMap.get("content"));
                infoContentService.update(infoContent);
            }
        }

    }

    private String setContentTag(String content, List<Tags> tagList, String type) {
        String contentNew = content;
        String urlPrefix = "";
        if (type.equals(InfoType.news.getName())) {
            urlPrefix = "/news/tag/";
        } else if (type.equals(InfoType.news.getName())) {
            urlPrefix = "/video/tag/";
        }
        for (Tags tags : tagList) {
            String url = urlPrefix + tags.getId() + "_1.html";
            String aTag = "<a href=\"" + url + "\"  target=\"_blank\">" + tags.getName() + "</a>";
            contentNew = contentNew.replaceFirst(tags.getName(), aTag);
        }
        return contentNew;
    }

    private List<Info> findInfoList(int offset, int fetchSize) {
        Info info = new Info();
        info.setSortIfDesc(true);
        info.setSortName("id");
        return infoService.list(info, offset, fetchSize);
    }

    //将uuid放入静态变量中
    private synchronized void setUUidMem() {
        if (Constants.INFO_UUID_SET.isEmpty()) {
            int offset = 0;
            int fetchSize = 1000;
            while (true) {
                logger.info("资讯信息放入内存第{}页", (offset / fetchSize) + 1);
                List<Info> infoList = findInfoList(offset, fetchSize);
                if (infoList == null || infoList.isEmpty()) {
                    break;
                }
                for (Info info : infoList) {
                    if (StringUtils.hasText(info.getUuid())) {
                        Constants.INFO_UUID_SET.add(info.getUuid());
                    }
                }
                offset = offset + fetchSize;
            }
            logger.info("成功将全部资讯信息放入内存总数{}", Constants.INFO_UUID_SET.size());
        }
    }


}
