package com.ygccw.wechat.common.info.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum InfoVideoType implements NamedEnum {
    matchVideo("比赛视频"), anchorVideo("主播视频"), playerVideo("玩家视频"), funVideo("娱乐视频"), originalVideo("原创视频");
    private String label;

    InfoVideoType(String label) {
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
