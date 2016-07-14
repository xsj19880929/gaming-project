package com.ygccw.wechat.common.picture.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum PictureZoneType implements NamedEnum {
    matchZone("赛事"), anchorZone("主播"), trade("行业");
    private String label;

    PictureZoneType(String label) {
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
