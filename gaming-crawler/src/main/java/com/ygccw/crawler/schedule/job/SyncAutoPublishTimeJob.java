package com.ygccw.crawler.schedule.job;


import com.ygccw.crawler.schedule.service.SyncAutoPublishTimeService;
import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import core.framework.scheduler.Job;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 *         按设定时间发布资讯
 */
public class SyncAutoPublishTimeJob implements Job {
    @Inject
    SyncAutoPublishTimeService syncAutoPublishTimeService;
    @Inject
    CrJobService crJobService;

    @Override
    public void execute() throws Throwable {
        CrJob crJob = crJobService.findByClassName(this.getClass().getName());
        Date lastTime = new Date();
        crJob.setLastTime(lastTime);
        syncAutoPublishTimeService.updateVerify();
        crJobService.update(crJob);
    }
}
