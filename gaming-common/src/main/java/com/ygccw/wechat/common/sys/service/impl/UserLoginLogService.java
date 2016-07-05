package com.ygccw.wechat.common.sys.service.impl;

import com.ygccw.wechat.common.sys.dao.UserLoginLogDao;
import com.ygccw.wechat.common.sys.entity.UserLoginLog;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author nick.guo
 */
@Service
public class UserLoginLogService {
    @Inject
    UserLoginLogDao userLoginLogDao;

    @Transactional
    public void save(UserLoginLog userLoginLog) {
        Date date = new Date();
        userLoginLog.setCreateTime(date);
        userLoginLog.setLastUpdateTime(date);
        userLoginLog.setLoginTime(date);
        userLoginLogDao.save(userLoginLog);
    }
}
