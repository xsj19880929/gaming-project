package com.ygccw.wechat.sys.utils;

import com.ygccw.wechat.common.sys.enums.UserType;


/**
 * Created by guoshuai on 16/5/20.
 */
public final class UserTypeRouter {
    private UserTypeRouter() {
    }

    public static UserType routerUp(UserType userType) {
        switch (userType) {
            case STAFF:
                return UserType.DISTRIBUTOR;
            case DISTRIBUTOR:
                return UserType.COMPANY_ADMIN;
            case COMPANY_ADMIN:
                return UserType.SUPER_ADMIN;
            default:
                break;
        }
        return null;
    }
}
