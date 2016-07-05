package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.constant.SessionKeys;
import core.framework.web.site.session.RequireSession;
import core.framework.web.site.session.SessionContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by guoshuai on 16/5/23.
 */
@RestController
public class CurrentSysUserController {
    @Inject
    SessionContext sessionContext;

    @RequireSession
    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    @ResponseBody
    public SysUser getCurrent() {
        return sessionContext.get(SessionKeys.SYS_USER);
    }
}
