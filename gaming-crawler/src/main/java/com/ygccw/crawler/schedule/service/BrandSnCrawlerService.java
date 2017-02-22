package com.ygccw.crawler.schedule.service;


import com.ygccw.crawler.common.CrawlerBase;
import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BrandSnCrawlerService {
    private final Logger logger = LoggerFactory.getLogger(BrandSnCrawlerService.class);
    @Inject
    private CrawlerBase crawlerBase;

    public void startTread(int threadNum) {
        // 生成任务
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
        CrCrawlTask crCrawlTask = new CrCrawlTask();
        crCrawlTask.setTempleName("15warticle");
        crCrawlTask.setUrl("http://www.15w.com/bagua/5606820885.html");
        JSONObject task = JSONObject.fromObject(crCrawlTask);
        task.put("infoZoneType", "trade");
        task.put("infoType", "news");
        logger.info("当前任务： {} ", task.toString());
        try {
            // 获取数据
            HashMap<String, List<HashMap<String, String>>> results = crawlerBase.crawler(task);
            System.out.println(results);
            // 数据写入
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
