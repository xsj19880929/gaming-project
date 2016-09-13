package com.ygccw.wechat.imagecode;

import com.ygccw.wechat.SpringTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class HospitalRegisterServiceTest extends SpringTest {
    @Inject
    HospitalRegisterService hospitalRegisterService;

    @Test
    @Rollback
    public void test() {
        try {
            hospitalRegisterService.testDo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
