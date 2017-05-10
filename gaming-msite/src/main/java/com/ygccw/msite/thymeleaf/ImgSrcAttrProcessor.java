package com.ygccw.msite.thymeleaf;

import com.google.common.base.Strings;
import com.ygccw.msite.utils.CookieKeyDefine;
import core.api.file.token.TokenBuilder;
import core.framework.util.StringUtils;
import core.framework.web.site.SiteSettings;
import core.framework.web.site.cookie.CookieContext;
import core.framework.web.site.cookie.CookieSpec;
import core.framework.web.site.template.BaseUrlProcessor;
import org.springframework.core.env.Environment;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * @author rainbow.cai
 *         copy from core2.0
 */
public class ImgSrcAttrProcessor extends BaseUrlProcessor {
    @Inject
    Environment env;
    @Inject
    CookieContext cookieContext;
    @Inject
    SiteSettings siteSettings;
    private CookieSpec<String> sessionId;
    private String imgServerUrl;
    private String fileServerUrl;

    public ImgSrcAttrProcessor(String imgServerUrl, List<String> baseCdnUrls) {
        this(imgServerUrl, "", baseCdnUrls);
    }

    public ImgSrcAttrProcessor(String imgServerUrl, String fileServerUrl, List<String> baseCdnUrls) {
        super("img-src", imgServerUrl, baseCdnUrls);
        this.imgServerUrl = imgServerUrl;
        this.fileServerUrl = fileServerUrl;
    }

    @PostConstruct
    public void init() {
        sessionId = CookieKeyDefine.PRIVATE_ID.path("/").sessionScope().httpOnly().domain(env.getProperty("private.cookie.domain"));
    }

    @Override
    protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        try {
//            final String url = eval(element.getAttributeValue(attributeName), arguments);
            final String url = eval(Strings.isNullOrEmpty(element.getAttributeValue(attributeName)) ? element.getAttributeValue("src")
                            : element.getAttributeValue(attributeName)
                    , arguments);
//            final String url = Strings.isNullOrEmpty(element.getAttributeValue(attributeName)) ? element.getAttributeValue("src")
//                    : element.getAttributeValue(attributeName);

            StringBuilder urlBuilder = new StringBuilder();
            if (!StringUtils.hasText(url)) {
                element.removeAttribute(attributeName);
                return ProcessorResult.OK;
            }
            if (url.startsWith("http:") || url.startsWith("https:")) {
                element.setAttribute("src", url);
                element.removeAttribute(attributeName);
                return ProcessorResult.OK;
            }

            if (url.contains("/private/")) {
                if (org.apache.commons.lang3.StringUtils.isEmpty(cookieContext.getCookie(sessionId))) {
                    cookieContext.setCookie(sessionId, UUID.randomUUID().toString());
                }

                String src = deleteDuplicateSlant(formalize(fileServerUrl, deleteDuplicateSlant(url) + "?token=" + token(url)));
                element.setAttribute("src", src);
                element.removeAttribute(attributeName);
                return ProcessorResult.OK;
            }

            String width = element.getAttributeValue("width");
            String height = element.getAttributeValue("height");
            if (url.startsWith("/image/") || url.startsWith("/static/")) {
                urlBuilder.append(deleteDuplicateSlant(url));
            } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(width) && org.apache.commons.lang3.StringUtils.isNotEmpty(height)) {
                // String width = element.getAttributeValue("width");
                //  String height = element.getAttributeValue("height");
                urlBuilder.append("/image/").append(getNum(width));
                urlBuilder.append('x');
                urlBuilder.append(getNum(height));
                urlBuilder.append(url);
            } else {
                urlBuilder.append("/image").append(url);
            }

            String imgServerUrl = this.imgServerUrl;
            /*if (isCDNEnable(element.getAttributeValue("th:cdn"))) {
                Preconditions.checkState(!getBaseCdnUrls().isEmpty());
                int indexInCdnUrls = (int) Math.abs(Hashing.md5().hashBytes(url.getBytes()).asInt()) % getBaseCdnUrls().size();
                imgServerUrl = getBaseCdnUrls().get(indexInCdnUrls);
                element.removeAttribute("th:cdn");
            } else*/
            if (url.startsWith("/static/")) {
                imgServerUrl = siteSettings.baseUrl();
            }
            String crop = element.getAttributeValue("th:crop");
            String src;
            if (!Strings.isNullOrEmpty(crop) && "true".equals(crop)) {
                element.removeAttribute("th:crop");
                src = deleteDuplicateSlant(formalize(imgServerUrl, urlBuilder.toString() + "?crop=true"));
            } else src = deleteDuplicateSlant(formalize(imgServerUrl, urlBuilder.toString()));
            element.setAttribute("src", src);
            element.removeAttribute(attributeName);
        } catch (RuntimeException e) {
            throw e;
        }
        return ProcessorResult.OK;
    }

    public String deleteDuplicateSlant(String url) {
        StringBuilder b = new StringBuilder(url);
        for (int i = 0; i < b.length(); i++) {
            if (b.charAt(i) == '/' && b.charAt(i + 1) == '/' && (i == 0 || (i > 0 && b.charAt(i - 1) != ':'))) {
                b.deleteCharAt(i);
                i--;
            }
        }
        return b.toString();
    }

    private String token(String path) {
        String cookie = cookieContext.getCookie(sessionId);
        try {
            return new TokenBuilder().setPath(org.apache.commons.lang3.StringUtils.substringAfter(path, "/file"))
                    .setCookie(cookie).build().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getNum(String word) {
        char[] chars = word.toCharArray();
        StringBuilder b = new StringBuilder();
        for (char c : chars) {
            if (c < '0' || c > '9') {
                return Integer.parseInt(b.toString());
            }
            b.append(c);
        }
        return Integer.parseInt(word);
    }

    /*public boolean isCDNEnable(String cdnValue) {
        return cdnValue != null && "true".equalsIgnoreCase(cdnValue);
    }*/

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
