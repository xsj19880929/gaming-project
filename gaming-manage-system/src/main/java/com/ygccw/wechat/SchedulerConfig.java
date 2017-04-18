package com.ygccw.wechat;

import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.enums.JobType;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import core.framework.scheduler.DefaultSchedulerConfig;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.List;

/**
 * @author soldier
 */
@Configuration
public class SchedulerConfig extends DefaultSchedulerConfig {
    private final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);
    @Inject
    Environment env;
    @Inject
    private CrJobService crJobService;

    @Override
    protected void configure(JobRegistry registry) throws Exception {
        if (!env.getRequiredProperty("job.enable", Boolean.class)) return;
//        registry.triggerByCronExpression(new SyncDataJob(), env.getRequiredProperty("job.syncDataJob"));
//        registry.triggerByCronExpression(new SyncAutoPublishTimeJob(), env.getRequiredProperty("job.syncAutoPublishTimeJob"));
////        registry.triggerByCronExpression(new SyncDataJob(), env.getRequiredProperty("job.syncData"));

        List<CrJob> crJobList = crJobService.list(JobType.sync);
        logger.info("job count = {}", crJobList.size());
        for (CrJob crJob : crJobList) {
            try {
                Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(crJob.getJobClass());
                Job job = jobClass.newInstance();
                registry.triggerByCronExpression(job, crJob.getCronExpression());

                //modified end
                logger.info(crJob.getJobName() + " start successful!");
            } catch (RuntimeException e) {
                logger.error(crJob.getJobName() + " start failed! exception:{}", e);
                crJob.setStatus(-1);
                crJob.setLastException(e.getMessage());
            } catch (Exception e) {
                logger.error(crJob.getJobName() + " start failed! exception:{}", e);
                crJob.setStatus(-1);
                crJob.setLastException(e.getMessage());
            } finally {
                crJobService.update(crJob);

            }
        }
    }


}
