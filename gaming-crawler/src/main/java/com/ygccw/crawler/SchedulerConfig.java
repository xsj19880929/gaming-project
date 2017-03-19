package com.ygccw.crawler;

import com.ygccw.crawler.schedule.job.ImageCrawlerJob;
import com.ygccw.crawler.schedule.job.InfoCrawlerJob;
import com.ygccw.crawler.schedule.job.SyncDataJob;
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
        registry.triggerByCronExpression(new ImageCrawlerJob(), env.getRequiredProperty("job.imageCrawler"));
        registry.triggerByCronExpression(new InfoCrawlerJob(), env.getRequiredProperty("job.infoCrawler"));
        registry.triggerByCronExpression(new SyncDataJob(), env.getRequiredProperty("job.syncData"));
    }


}
