package com.ygccw.crawler.schedule.job;

import com.ygccw.crawler.schedule.service.ImageCrawlerService;
import core.framework.scheduler.Job;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class ImageCrawlerJob implements Job {
    @Inject
    ImageCrawlerService imageCrawlerService;

    @Override
    public void execute() throws Throwable {
        imageCrawlerService.startTread(1);
    }
}
