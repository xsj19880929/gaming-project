package com.ygccw.crawler.schedule.job;

import com.ygccw.crawler.schedule.service.InfoCrawlerService;
import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import core.framework.scheduler.Job;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 */
public class InfoCrawlerJob implements Job {
    @Inject
    InfoCrawlerService infoCrawlerService;
    @Inject
    CrJobService crJobService;

    @Override
    public void execute() throws Throwable {
        CrJob crJob = crJobService.findByClassName(this.getClass().getName());
        Date lastTime = new Date();
        crJob.setLastTime(lastTime);
        infoCrawlerService.startTread(crJob.getThreadNum());
        crJobService.update(crJob);
    }
}
