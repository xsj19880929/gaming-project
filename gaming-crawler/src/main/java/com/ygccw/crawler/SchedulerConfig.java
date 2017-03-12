package com.ygccw.crawler;

import core.framework.scheduler.DefaultSchedulerConfig;
import core.framework.scheduler.JobRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Configuration
public class SchedulerConfig extends DefaultSchedulerConfig {
    @Inject
    Environment env;

    @Override
    protected void configure(JobRegistry registry) throws Exception {
        if (!env.getRequiredProperty("job.enable", Boolean.class)) return;
//        registry.triggerByCronExpression(refreshCarTypeExpiredScheduler(), env.getRequiredProperty("job.refreshCarTypeExpired"));
//        registry.triggerByCronExpression(new QuotationTaskQueJob(), "0 10 * * * ?");
//        registry.triggerByCronExpression(new SyncInsuranceGiftFromJNJob(), "0 0 4 * * ?");
    }


}
