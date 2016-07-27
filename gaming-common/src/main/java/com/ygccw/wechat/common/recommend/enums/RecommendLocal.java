package com.ygccw.wechat.common.recommend.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum RecommendLocal implements NamedEnum {
    index("首页"), matchZoneVideo("首页比赛视频推荐"), anchorZoneVideo("首页主播视频推荐");
    private String label;

    RecommendLocal(String label) {
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
