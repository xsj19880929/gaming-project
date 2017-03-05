package com.ygccw.crawler;


import core.api.file.FileConfiguration;
import core.api.file.FileServerSettings;
import core.enumeration.enumeration.EnableEnum;
import core.framework.http.HTTPClient;
import core.framework.web.DefaultSiteConfig;
import core.framework.web.site.cookie.CookieContext;
import core.framework.web.site.cookie.CookieInterceptor;
import core.framework.web.site.scheme.HTTPSchemeEnforceInterceptor;
import core.framework.web.site.session.SecureSessionContext;
import core.framework.web.site.session.SessionContext;
import core.framework.web.site.session.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.inject.Inject;


@Configuration
@ComponentScan(basePackageClasses = WebConfig.class, basePackages = {"com.ygccw.wechat.common"})
@Import({FileConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableEnum
public class WebConfig extends DefaultSiteConfig {
    @Inject
    Environment env;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(httpSchemeEnforceInterceptor());
        registry.addInterceptor(cookieInterceptor());
        registry.addInterceptor(sessionInterceptor());

    }


    @Bean
    public CookieInterceptor cookieInterceptor() {
        return new CookieInterceptor();
    }


    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Bean
    public HTTPSchemeEnforceInterceptor httpSchemeEnforceInterceptor() {
        return new HTTPSchemeEnforceInterceptor();
    }


    @Bean
    public HTTPClient httpClient() {
        return new HTTPClient();
    }


    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    CookieContext cookieContext() {
        return new CookieContext();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    SessionContext sessionContext() {
        return new SessionContext();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    SecureSessionContext secureSessionContext() {
        return new SecureSessionContext();
    }

    @Bean
    public FileServerSettings fileServerSettings() {
        FileServerSettings fileServerSettings = new FileServerSettings();
        fileServerSettings.setServerUrl(env.getRequiredProperty("out.file.uploadServer"));
        return fileServerSettings;
    }
}
