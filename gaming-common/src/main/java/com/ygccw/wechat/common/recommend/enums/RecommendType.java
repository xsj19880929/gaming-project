package com.ygccw.wechat.common.recommend.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum RecommendType implements NamedEnum {
    matchZone("赛事"), anchorZone("主播"), news("资讯"), picture("图集");
    private String label;

    RecommendType(String label) {
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
