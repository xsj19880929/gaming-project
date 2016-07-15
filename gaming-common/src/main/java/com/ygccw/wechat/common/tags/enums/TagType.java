package com.ygccw.wechat.common.tags.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum TagType implements NamedEnum {
    video("视频"), news("新闻"), picture("图集");
    private String label;

    TagType(String label) {
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
