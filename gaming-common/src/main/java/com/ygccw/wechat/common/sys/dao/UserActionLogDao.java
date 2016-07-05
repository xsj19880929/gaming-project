package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.UserActionLog;
import core.framework.database.JPAAccess;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author nick.guo
 */
@Component
public class UserActionLogDao {
    @Inject
    JPAAccess jpaAccess;

    public void save(UserActionLog userActionLog) {
        this.jpaAccess.save(userActionLog);
    }
}
