package com.ygccw.msite.thymeleaf;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.DocumentNodeProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.document.AbstractDocumentProcessor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author soldier
 */
//@Service
public class DocumentHeadProcessor extends AbstractDocumentProcessor {
    private static final DocumentNodeProcessorMatcher MATCHER = new DocumentNodeProcessorMatcher();
    //    final Logger logger = LoggerFactory.getLogger(AbstractDocumentProcessor.class);
    final List<Pattern> excludedPages = Lists.newArrayList();
    final List<Pattern> excludedUrls = Lists.newArrayList();
    @Inject
    HttpServletRequest request;
    private String headHtml;


    public DocumentHeadProcessor() {
        super(MATCHER);
        URL head = Resources.getResource("head.html");
        try {
            headHtml = Resources.toString(head, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文档名称 排除
     *
     * @param page
     * @return
     */
    public DocumentHeadProcessor excludedPages(String page) {
        excludedPages.add(Pattern.compile(page));
        return this;
    }

    /**
     * 根据请求地址 正则匹配 排除
     *
     * @param url
     * @return
     */
    public DocumentHeadProcessor excludedUrls(String url) {
        excludedUrls.add(Pattern.compile(url));
        return this;
    }

    @Override
    protected ProcessorResult processDocumentNode(Arguments arguments, Document document) {
        if (isUrlExcluded(request.getRequestURI())) return ProcessorResult.OK;
        if (document.getDocumentName() != null && isPageExcluded(document.getDocumentName())) return ProcessorResult.OK;

        for (Element element : document.getFirstElementChild().getElementChildren()) {
            if ("body".equals(element.getNormalizedName())) {
                element.insertChild(0, new Text(headHtml, null, null, true));
            }
        }
        return ProcessorResult.OK;
    }

    private boolean isUrlExcluded(String url) {
        for (Pattern pattern : excludedUrls) {
            if (pattern.matcher(url).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPageExcluded(String pageName) {
        for (Pattern pattern : excludedPages) {
            if (pattern.matcher(pageName).matches()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getPrecedence() {
        return 0;
    }

}
