package com.ygccw.crawler.schedule.service;

import com.ygccw.crawler.SpringTest;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class BrandSnCrawlerServiceTest extends SpringTest {
    @Inject
    BrandSnCrawlerService brandSnCrawlerService;

    @Test
    public void testStartTread() throws Exception {
        brandSnCrawlerService.startTread(1);
    }
}