package com.ygccw.crawler.schedule.job;

import com.ygccw.crawler.SpringTest;
import com.ygccw.crawler.schedule.service.SyncDataService;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class SyncDataJobTest extends SpringTest {
    @Inject
    SyncDataService syncDataService;

    @Test
    public void testExecute() throws Exception {
//        syncDataService.updateVerify();
    }
}