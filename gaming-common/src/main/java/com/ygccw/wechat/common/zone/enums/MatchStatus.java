package com.ygccw.wechat.common.zone.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum MatchStatus implements NamedEnum {
    START("即将开始"), END("已经结束"), DOING("正在进行");
    private String label;

    MatchStatus(String label) {
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
