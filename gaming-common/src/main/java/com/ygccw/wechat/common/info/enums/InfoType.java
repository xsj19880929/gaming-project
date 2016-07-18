package com.ygccw.wechat.common.info.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum InfoType implements NamedEnum {
    news("新闻"), video("视频");
    private String label;

    InfoType(String label) {
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
