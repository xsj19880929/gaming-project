package com.ygccw.msite.thymeleaf;

import com.google.common.base.Strings;
import core.framework.web.site.template.BaseUrlProcessor;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;

import java.util.List;

/**
 * @author lucian.lin
 */
public class LinkHrefAttrProcessor extends BaseUrlProcessor {
    private final String version;

    public LinkHrefAttrProcessor(String baseUrl, List<String> baseCdnUrls, String version) {
        super("href", baseUrl, baseCdnUrls);
        this.version = version;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String href = Strings.isNullOrEmpty(element.getAttributeValue(attributeName)) ? element.getAttributeValue("href")
                : element.getAttributeValue(attributeName);

        String url = eval(href, arguments);

        if (isRelativeUrl(url)) {
            String baseUrl = baseUrl();
            /*if (isCDNEnable(element.getAttributeValue("th:cdn")) && "link".equals(element.getNormalizedName())) {
                Preconditions.checkState(!getBaseCdnUrls().isEmpty());
                int indexInCdnUrls = (int) Math.abs(Hashing.md5().hashBytes(url.getBytes()).asInt()) % getBaseCdnUrls().size();
                baseUrl = getBaseCdnUrls().get(indexInCdnUrls);
//                element.setAttribute("href", formalize(getBaseCdnUrls().get(indexInCdnUrls), url));
//                element.removeAttribute(attributeName);
                element.removeAttribute("th:cdn");
            }*/

            if (isCSSLinkElement(element)) {
                String fullURL = formalize(baseUrl, url);
                fullURL += fullURL.contains("?") ? "&v=" + version : "?v=" + version;
                element.setAttribute("href", fullURL);
            } else {
                element.setAttribute("href", formalize(baseUrl, url));
            }
        } else {
            element.setAttribute("href", url);
        }

        element.removeAttribute(attributeName);
        return ProcessorResult.OK;
    }

    protected boolean isCSSLinkElement(Element element) {
        return "link".equals(element.getNormalizedName()) && !Strings.isNullOrEmpty(element.getAttributeValue("href"));
    }

    boolean isRelativeUrl(String url) {
        return url != null && !url.startsWith("http:/") && !url.startsWith("https:/");
    }

    /*boolean isCDNEnable(String cdnValue) {
        return cdnValue != null && "true".equalsIgnoreCase(cdnValue);
    }*/

}
