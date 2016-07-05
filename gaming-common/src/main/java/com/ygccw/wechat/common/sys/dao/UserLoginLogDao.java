package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.UserLoginLog;
import core.framework.database.JPAAccess;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author nick.guo
 */
@Component
public class UserLoginLogDao {
    @Inject
    JPAAccess jpaAccess;

    public void save(UserLoginLog userLoginLog) {
        jpaAccess.save(userLoginLog);
    }
}
