package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.sys.SecuritySettings;
import core.framework.web.site.session.RequireSession;
import core.framework.web.site.session.SessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nick.guo
 */
@Controller
public class LogoutController {

    @Inject
    SessionContext sessionContext;
    @Inject
    SecuritySettings securitySettings;


    @RequireSession
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletResponse response) throws IOException {
        sessionContext.invalidate();
        response.sendRedirect(securitySettings.getLogout());
    }

}
