package com.ygccw.wechat.common.info.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum InfoZoneType implements NamedEnum {
    matchZone("赛事"), anchorZone("主播"), trade("行业");
    private String label;

    InfoZoneType(String label) {
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
