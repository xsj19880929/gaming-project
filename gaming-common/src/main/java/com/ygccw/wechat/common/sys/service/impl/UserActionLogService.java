package com.ygccw.wechat.common.sys.service.impl;

import com.ygccw.wechat.common.sys.dao.UserActionLogDao;
import com.ygccw.wechat.common.sys.entity.UserActionLog;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author nick.guo
 */
@Service
public class UserActionLogService {
    @Inject
    UserActionLogDao userActionLogDao;

    @Transactional
    public void save(UserActionLog userActionLog) {
        userActionLog.setCreateTime(new Date());

        userActionLogDao.save(userActionLog);
    }
}
