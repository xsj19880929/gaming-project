package com.ygccw.crawler.schedule.job;

import com.ygccw.crawler.schedule.service.CreateUrlService;
import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import core.framework.scheduler.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 */
public class CreateUrlJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    CreateUrlService createUrlService;
    @Inject
    CrJobService crJobService;

    @Override
    public void execute() throws Throwable {
        CrJob crJob = crJobService.findByClassName(this.getClass().getName());
        Date lastTime = crJob.getLastTime();
        crJob.setLastTime(new Date());
        crJobService.update(crJob);
        try {
            createUrlService.updateVerify(lastTime == null ? new Date() : lastTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            crJob.setLastException(e.getMessage());
        }
        crJobService.update(crJob);
    }
}
