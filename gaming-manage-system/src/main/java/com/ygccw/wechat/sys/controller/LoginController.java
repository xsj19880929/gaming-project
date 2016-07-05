package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.common.sys.entity.UserLoginLog;
import com.ygccw.wechat.common.sys.service.SysUserService;
import com.ygccw.wechat.constant.SessionKeys;
import com.ygccw.wechat.sys.Menu;
import com.ygccw.wechat.sys.SecurityInterceptor;
import com.ygccw.wechat.sys.SecuritySettings;
import core.framework.web.filter.RemoteAddress;
import core.framework.web.site.cookie.CookieContext;
import core.framework.web.site.cookie.CookieSpec;
import core.framework.web.site.session.RequireSession;
import core.framework.web.site.session.SessionContext;
import core.framework.web.site.session.SessionKey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nick.guo
 */
@RestController
public class LoginController {
    @Inject
    SysUserService sysUserService;
    @Inject
    SessionContext sessionContext;
    @Inject
    SecuritySettings securitySettings;
    @Inject
    CookieContext cookieContext;

    @RequireSession
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@Valid String username, @Valid String password, HttpServletResponse response, HttpServletRequest
            request) throws IOException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        SysUser oUser = sysUserService.authentication(sysUser);
        /*Map map = new HashMap();
        map.put("username", oUser.gec jytUsername());*/
        oUser.setPassword(null);
        oUser.setSalt(null);
        sessionContext.set(SessionKeys.SYS_USER, oUser);
        userLoginLogBuilder(request, username, "");
        response.sendRedirect(sessionContext.get(SessionKey.stringKey(SecurityInterceptor.PATH)));
    }

    @RequireSession
    @RequestMapping(value = "/login-url", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getCasLoginUrl(@RequestParam(SecurityInterceptor.PATH) String path) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginUrl", securitySettings.getUnauthenticatedUrl());
        sessionContext.set(SessionKey.key(SecurityInterceptor.PATH, String.class), path);
        return map;
    }

    @RequireSession
    @RequestMapping(value = "/menu/test", method = RequestMethod.GET)
    @Menu({"product.list"})
    public void test() {
        System.out.println("test");
    }

    private UserLoginLog userLoginLogBuilder(HttpServletRequest request, String username, String wxUsername) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserAgent(request.getHeader("User-Agent"));
        userLoginLog.setSessionId(cookieContext.getCookie(CookieSpec.stringKey("SessionId")));
        userLoginLog.setUsername(username);
        userLoginLog.setIp(RemoteAddress.create(request).getClientIP());
        userLoginLog.setWxUsername(wxUsername);
        return userLoginLog;
    }
}
