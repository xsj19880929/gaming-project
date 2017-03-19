package com.ygccw.crawler.schedule.job;

import com.ygccw.crawler.schedule.service.InfoCrawlerService;
import core.framework.scheduler.Job;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class InfoCrawlerJob implements Job {
    @Inject
    InfoCrawlerService infoCrawlerService;

    @Override
    public void execute() throws Throwable {
        infoCrawlerService.startTread(1);
    }
}
