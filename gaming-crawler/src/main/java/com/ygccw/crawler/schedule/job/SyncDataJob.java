package com.ygccw.crawler.schedule.job;


import com.ygccw.crawler.schedule.service.SyncDataService;
import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import core.framework.scheduler.Job;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 *         定时发布一条资讯
 */
public class SyncDataJob implements Job {
    @Inject
    SyncDataService syncDataService;
    @Inject
    CrJobService crJobService;

    @Override
    public void execute() throws Throwable {
        CrJob crJob = crJobService.findByClassName(this.getClass().getName());
        Date lastTime = new Date();
        crJob.setLastTime(lastTime);
        syncDataService.updateVerify();
        crJobService.update(crJob);
    }
}
