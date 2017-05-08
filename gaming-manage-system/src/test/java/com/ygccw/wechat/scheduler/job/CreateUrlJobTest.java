package com.ygccw.wechat.scheduler.job;

import com.ygccw.wechat.SpringTest;
import com.ygccw.wechat.scheduler.service.CreateUrlService;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author soldier
 */
public class CreateUrlJobTest extends SpringTest {
    @Inject
    CreateUrlService createUrlService;

    @Test
    public void test() throws Exception {
        createUrlService.updateVerify(new Date());
    }

}