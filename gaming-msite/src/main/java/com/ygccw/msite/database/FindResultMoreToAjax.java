package com.ygccw.msite.database;

import com.ygccw.msite.mobile.common.model.HtmlTemplate;

import java.util.List;

/**
 * @author soldier
 */
public class FindResultMoreToAjax<T> {
    private final List<T> list;
    private final HtmlTemplate htmlTemplate;

    public FindResultMoreToAjax(List<T> list, HtmlTemplate htmlTemplate) {
        this.list = list;
        this.htmlTemplate = htmlTemplate;
    }

    public List<T> getList() {
        return list;
    }

    public HtmlTemplate getHtmlTemplate() {
        return htmlTemplate;
    }
}
