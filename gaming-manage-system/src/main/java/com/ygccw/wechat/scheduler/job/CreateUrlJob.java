package com.ygccw.wechat.scheduler.job;

import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import com.ygccw.wechat.scheduler.service.CreateUrlService;
import core.framework.scheduler.Job;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 */
public class CreateUrlJob implements Job {
    @Inject
    CreateUrlService createUrlService;
    @Inject
    CrJobService crJobService;

    @Override
    public void execute() throws Throwable {
        CrJob crJob = crJobService.findByClassName(this.getClass().getName());
        Date lastTime = new Date();
        createUrlService.updateVerify(crJob.getLastTime() == null ? lastTime : crJob.getLastTime());
        crJob.setLastTime(lastTime);
        crJobService.update(crJob);
    }
}
