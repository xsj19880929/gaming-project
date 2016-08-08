package com.ygccw.website.thymeleaf;

import com.google.common.collect.Sets;
import core.framework.web.site.template.DefaultDialect;
import core.framework.web.site.template.DefaultSrcAttrProcessor;
import core.framework.web.site.template.MetaImgAttrProcessor;
import core.framework.web.site.template.PageAttrProcessor;
import core.framework.web.site.template.PaginationProcessor;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.attr.StandardHrefAttrProcessor;
import org.thymeleaf.standard.processor.attr.StandardSrcAttrProcessor;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author soldier
 */
public class MobileDialect extends DefaultDialect {
    private final String baseUrl;
    private final List<String> baseCdnUrls;
    private final String imageServerUrl;
    private final String version = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    //    @Inject
//    DocumentHeadProcessor documentHeadProcessor;
    @Inject
    private ImgSrcAttrProcessor imgSrcAttrProcessor;


    public MobileDialect(String baseUrl, String imageServerUrl, List<String> baseCdnUrls) {
        super(baseUrl, imageServerUrl, baseCdnUrls);
        this.baseUrl = baseUrl;
        this.baseCdnUrls = baseCdnUrls;
        this.imageServerUrl = imageServerUrl;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = Sets.newHashSet();

        for (IProcessor processor : createStandardProcessorsSet()) {
            if (!(processor instanceof StandardSrcAttrProcessor)
                    && !(processor instanceof StandardHrefAttrProcessor)) {
                processors.add(processor);
            }
        }
        processors.add(new LinkHrefAttrProcessor(baseUrl, baseCdnUrls, version));
        processors.add(new DefaultSrcAttrProcessor(baseUrl, baseCdnUrls, version));
        processors.add(new PaginationProcessor(baseUrl));
        processors.add(new MetaImgAttrProcessor(imageServerUrl, baseCdnUrls));
        processors.add(new PageAttrProcessor());
        processors.add(new CdnProcessor(baseUrl, baseCdnUrls, version));
        processors.add(imgSrcAttrProcessor);
//        processors.add(documentHeadProcessor);


        return processors;
    }
}
