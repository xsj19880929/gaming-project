package com.ygccw.wechat.commiturlsearch.service;

import com.ygccw.wechat.SpringTest;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class CommitUrlSearchModelServiceTest extends SpringTest {
    @Inject
    CommitUrlSearchModelService commitUrlSearchModelService;

    @Test
    public void test() throws Exception {
        commitUrlSearchModelService.create360SiteMap();
    }

}