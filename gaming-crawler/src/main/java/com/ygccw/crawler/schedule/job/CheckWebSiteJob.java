package com.ygccw.crawler.schedule.job;


import com.ygccw.crawler.schedule.service.CheckWebSiteService;
import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import core.framework.scheduler.Job;
import core.framework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 *         定时检测网站运行情况
 */
public class CheckWebSiteJob implements Job {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    CheckWebSiteService checkWebSiteService;
    @Inject
    CrJobService crJobService;

    @Override
    public void execute() throws Throwable {
        StopWatch stopWatch = new StopWatch();
        CrJob crJob = crJobService.findByClassName(this.getClass().getName());
        logger.info("{}任务开始", crJob.getJobName());
        Date lastTime = new Date();
        crJob.setLastTime(lastTime);
        checkWebSiteService.startTread();
        crJobService.update(crJob);
        logger.info("{}任务结束,耗时{}分钟", crJob.getJobName(), stopWatch.elapsedTime() / (1000 * 60));
    }
}
