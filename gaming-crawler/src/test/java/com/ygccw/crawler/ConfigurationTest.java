package com.ygccw.crawler;

import core.framework.exception.Ignore;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;

/**
 * @author neo
 */
@Ignore
public class ConfigurationTest extends SpringTest {
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void contextShouldBeInitialized() {
        Assert.assertNotNull(applicationContext);
    }

    @Test
    public void verifyBeanConfiguration() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            applicationContext.getBean(beanName);
        }
    }
}
