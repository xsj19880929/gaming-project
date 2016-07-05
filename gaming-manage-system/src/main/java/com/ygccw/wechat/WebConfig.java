package com.ygccw.wechat;

import com.ygccw.wechat.sys.SecurityInterceptor;
import com.ygccw.wechat.sys.SecuritySettings;
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
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.inject.Inject;


@Configuration
@ComponentScan(basePackageClasses = WebConfig.class, basePackages = {"com.ygccw.wechat.common"})
@Import({FileConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableEnum
public class WebConfig extends DefaultSiteConfig {
    @Inject
    Environment env;

    @Bean
    public SecuritySettings securitySettings() {
        SecuritySettings securitySettings = new SecuritySettings();
        securitySettings.setUnauthenticatedUrl("/login.html");
        securitySettings.addExcludeUrl("/www/view/login.html");
        securitySettings.addExcludeUrl("/logout");
        securitySettings.addExcludeUrl("/login");
        securitySettings.addExcludeUrl("/api/*");
        securitySettings.addExcludeUrl("/enum/*");
        securitySettings.addExcludeUrl("/login-url");
        securitySettings.addExcludeUrl("/health-check");
        securitySettings.addExcludeUrl("/ueditor/config");
        securitySettings.addExcludeUrl("/image/server");
        securitySettings.addExcludeUrl("/image/upload");
        return securitySettings;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(httpSchemeEnforceInterceptor());
        registry.addInterceptor(cookieInterceptor());
        registry.addInterceptor(sessionInterceptor());
        registry.addInterceptor(securityInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/www/**").addResourceLocations("/www/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("favicon.ico").addResourceLocations("/");
        registry.addResourceHandler("index.html").addResourceLocations("/");
        registry.addResourceHandler("login.html").addResourceLocations("/");
        registry.addResourceHandler("/ue/**").addResourceLocations("/ue/");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
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
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
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
        fileServerSettings.setImageUrl(env.getRequiredProperty("out.image.downloadUrl"));
        return fileServerSettings;
    }
}
