package com.ygccw.wechat.common.link.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum SiteType implements NamedEnum {
    pc("pc端"), m("手机端");
    private String label;

    SiteType(String label) {
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
