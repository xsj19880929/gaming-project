package com.ygccw.crawler.schedule.service;

import com.ygccw.crawler.SpringTest;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class CheckWebSiteServiceTest extends SpringTest {
    @Inject
    private CheckWebSiteService checkWebSiteService;

    @Test
    public void testStartTread() throws Exception {
        checkWebSiteService.startTread();
    }
}