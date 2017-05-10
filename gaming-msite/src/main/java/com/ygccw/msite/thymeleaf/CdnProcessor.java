package com.ygccw.msite.thymeleaf;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import core.framework.util.StringUtils;
import core.framework.web.site.template.BaseUrlProcessor;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;

import java.util.List;

/**
 * @author miller
 */
public class CdnProcessor extends BaseUrlProcessor {
    private final String version;

    public CdnProcessor(String baseUrl, List<String> baseCdnUrls, String version) {
        super("cdn", baseUrl, baseCdnUrls);
        this.version = version;
    }

    @Override
    protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String attributeKey = getAttributeKey(element);
        if (null == attributeKey) {
            return ProcessorResult.OK;
        }
        final String url = eval(element.getAttributeValue(attributeKey), arguments);
        if (!StringUtils.hasText(url)) {
            element.removeAttribute(attributeName);
            return ProcessorResult.OK;
        }
        if (isRelativeUrl(url)) {
            Preconditions.checkState(!getBaseCdnUrls().isEmpty());
            int indexInCdnUrls = (int) Math.abs(Hashing.md5().hashBytes(url.getBytes()).asInt()) % getBaseCdnUrls().size();
            String baseUrl = getBaseCdnUrls().get(indexInCdnUrls);
            if (!StringUtils.hasText(baseUrl)) {
                baseUrl = baseUrl();
            }
            if (hasVersion(element.getAttributeValue("th:cdn"))) {
                String fullURL = formalize(baseUrl, url);
                fullURL += fullURL.contains("?") ? "&v=" + version : "?v=" + version;
                element.setAttribute(attributeKey, fullURL);
            } else {
                element.setAttribute(attributeKey, formalize(baseUrl, url));
            }
        }
        element.removeAttribute(attributeName);
        return ProcessorResult.OK;
    }

    private String getAttributeKey(Element element) {
        if ("link".equals(element.getNormalizedName()) && !Strings.isNullOrEmpty(element.getAttributeValue("href"))) {
            return "href";
        } else if ("img".equals(element.getNormalizedName()) || "script".equals(element.getNormalizedName())) {
            return "src";
        }
        return null;
    }

    boolean isRelativeUrl(String url) {
        return url != null && !url.startsWith("http:/") && !url.startsWith("https:/");
    }

    boolean hasVersion(String cdnValue) {
        return cdnValue != null && ("version".equalsIgnoreCase(cdnValue) || "v".equalsIgnoreCase(cdnValue));
    }

    @Override
    public int getPrecedence() {
        return 999;
    }
}
