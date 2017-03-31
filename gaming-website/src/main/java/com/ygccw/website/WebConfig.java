package com.ygccw.website;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.ygccw.website.interceptor.AccessInterceptor;
import com.ygccw.website.thymeleaf.ImgSrcAttrProcessor;
import com.ygccw.website.thymeleaf.MobileDialect;
import core.api.file.FileServerSettings;
import core.framework.util.TimeLength;
import core.framework.web.DefaultSiteConfig;
import core.framework.web.runtime.RuntimeEnvironment;
import core.framework.web.runtime.RuntimeSettings;
import core.framework.web.site.SiteSettings;
import core.framework.web.site.session.SessionProviderType;
import core.framework.web.site.template.assets.AssetsController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.inject.Inject;
import java.util.Arrays;

/**
 *
 */
@Configuration
@ComponentScan(basePackageClasses = WebConfig.class, basePackages = {"com.ygccw.wechat.common"})
@EnableTransactionManagement(proxyTargetClass = true)
public class WebConfig extends DefaultSiteConfig {
    @Inject
    Environment env;
    @Inject
    MessageSource messages;


    @Override
    public RuntimeSettings runtimeSettings() {
        RuntimeSettings runtimeSettings = new RuntimeSettings();
        if ("PROD".equals(env.getProperty("site.env"))) {
            runtimeSettings.setEnvironment(RuntimeEnvironment.PROD);
        } else {
            runtimeSettings.setEnvironment(RuntimeEnvironment.DEV);
        }
        return runtimeSettings;
    }

    @Bean
    public AssetsController assetsController() {
        return new AssetsController();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(httpSchemeEnforceInterceptor());
        registry.addInterceptor(cookieInterceptor());
        registry.addInterceptor(sessionInterceptor());
        registry.addInterceptor(exceptionInterceptor());
        registry.addInterceptor(accessInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("favicon.ico").addResourceLocations("/");
        registry.addResourceHandler("index").addResourceLocations("/");
        registry.addResourceHandler("baidu_verify_MZA58sj9Pc.html").addResourceLocations("/");
        registry.addResourceHandler("sogousiteverification.txt").addResourceLocations("/");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public SiteSettings siteSettings() {
        SiteSettings siteSettings = new SiteSettings();
        siteSettings.setErrorPage("/500.html");
        siteSettings.setResourceNotFoundPage("/404.html");
        String baseUrl = env.getProperty("site.baseUrl");
        siteSettings.setBaseUrl(baseUrl);
        siteSettings.setSessionTimeOut(TimeLength.hours(env.getProperty("site.sessionTimeOut", Long.class, 6L)));
        siteSettings.setBaseCdnUrls(Arrays.asList(env.getProperty("site.baseCDNUrls", String.class, baseUrl).split(",")));
        siteSettings.setImgServerUrl(env.getProperty("out.image.downloadUrl", String.class, baseUrl));
        siteSettings.setTemplateDir(env.getProperty("site.templateDir"));
        siteSettings.setTemplateCached(env.getProperty("site.templateCached", boolean.class, true));
        siteSettings.setSessionEnabled(env.getProperty("site.sessionEnable", boolean.class, true));
        siteSettings.setCookieEnabled(env.getProperty("site.cookieEnable", boolean.class, true));
        siteSettings.setRedisMaxTotalForSession(env.getProperty("site.session.maxTotal", Integer.class, 8));
        if (StringUtils.isNotEmpty(env.getProperty("cache.server"))) {
            siteSettings.setSessionProviderType(SessionProviderType.REDIS);
            siteSettings.setRemoteSessionServer(env.getProperty("cache.server"));
            siteSettings.setRedisMaxTotalForSession(env.getProperty("site.session.maxTotal", Integer.class, 8));
//            siteSettings.setRedisMaxWaitMillisForSession(env.getProperty("site.session.maxWaitMillis", Long.class, 10000l));
        }
        return siteSettings;
    }

    @Bean
    public FileServerSettings fileServerSettings() {
        FileServerSettings fileServerSettings = new FileServerSettings();
        fileServerSettings.setServerUrl(env.getRequiredProperty("out.file.uploadServer"));
        fileServerSettings.setImageUrl(env.getRequiredProperty("out.image.downloadUrl"));
        return fileServerSettings;
    }

    @Bean
    MobileDialect mobileDialect() {
        SiteSettings siteSettings = siteSettings();
        return new MobileDialect(siteSettings.baseUrl(), siteSettings.getImgServerUrl(), siteSettings.baseCdnUrls());
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        SiteSettings siteSettings = siteSettings();

        TemplateResolver templateResolver = Strings.isNullOrEmpty(siteSettings.templateDir())
                ? new ServletContextTemplateResolver() : new FileTemplateResolver();

        templateResolver.setCharacterEncoding(Charsets.UTF_8.name());
        templateResolver.setPrefix(siteSettings.templateDir());
        templateResolver.setTemplateMode("LEGACYHTML5");

        if (!siteSettings.templateCached()) {
            templateResolver.setCacheable(false);
        }

        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setMessageSource(messages);
        templateEngine.setDialect(mobileDialect());

        return templateEngine;
    }

    @Bean
    public ImgSrcAttrProcessor imgSrcAttrProcessor() {
        SiteSettings siteSettings = siteSettings();
        return new ImgSrcAttrProcessor(env.getProperty("out.image.downloadUrl"), env.getProperty("out.file.uploadServer"), siteSettings.baseCdnUrls());
    }

    @Bean
    public AccessInterceptor accessInterceptor() {
        return new AccessInterceptor();
    }


}
