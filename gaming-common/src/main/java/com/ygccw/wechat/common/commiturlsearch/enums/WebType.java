package com.ygccw.wechat.common.commiturlsearch.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum WebType implements NamedEnum {
    baidu("百度"), sogou("搜狗"), so360("360搜索"), sm("神马");
    private String label;

    WebType(String label) {
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
