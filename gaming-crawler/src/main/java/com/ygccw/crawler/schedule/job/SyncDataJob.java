package com.ygccw.crawler.schedule.job;

import com.ygccw.crawler.schedule.service.SyncDataService;
import core.framework.scheduler.Job;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class SyncDataJob implements Job {
    @Inject
    SyncDataService syncDataService;

    @Override
    public void execute() throws Throwable {
        syncDataService.updateVerify();
    }
}
