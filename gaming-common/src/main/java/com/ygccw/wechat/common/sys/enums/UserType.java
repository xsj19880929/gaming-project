package com.ygccw.wechat.common.sys.enums;

import core.framework.util.NamedEnum;

/**
 * Created by guoshuai on 16/5/19.
 */
public enum UserType implements NamedEnum {

    SUPER_ADMIN("超级管理员"), COMPANY_ADMIN("公司管理员"), DISTRIBUTOR("经销商"), STAFF("员工");
    private String label;

    UserType(String label) {
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
