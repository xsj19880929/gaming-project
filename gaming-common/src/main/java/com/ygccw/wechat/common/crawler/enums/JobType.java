package com.ygccw.wechat.common.crawler.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum JobType implements NamedEnum {
    crawl("爬虫"), sync("同步");
    private String label;

    JobType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getName() {
        return name();
    }
}
