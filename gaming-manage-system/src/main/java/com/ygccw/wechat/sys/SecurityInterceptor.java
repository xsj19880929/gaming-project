package com.ygccw.wechat.sys;

import com.google.common.base.Preconditions;
import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.common.sys.exception.UserAuthenticationException;
import com.ygccw.wechat.common.sys.exception.UserAuthorizationException;
import com.ygccw.wechat.constant.SessionKeys;
import core.framework.web.site.ControllerHelper;
import core.framework.web.site.session.SessionContext;
import core.framework.web.site.session.SessionKey;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author neo
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    public static final String PATH = "path";
    @Inject
    SessionContext sessionContext;
    @Inject
    SecuritySettings securitySettings;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (securitySettings.isExcludeUrl(request.getRequestURI())) return true;
        rememberLastRequest(request);

        SysUser user = sessionContext.get(SessionKeys.SYS_USER);
        if (user == null) {
            throw new UserAuthenticationException("user not login");
        }
        Menu menu = ControllerHelper.findMethodOrClassLevelAnnotation(handler, Menu.class);
        if (null == menu) return true;
        SysMenuContext sysMenuContext = sessionContext.get(SessionKeys.SYS_MENU_CONTEXT);
        Preconditions.checkState(sysMenuContext != null, "sysMenuContext not exist,plz check menu load...");
        if (!sysMenuContext.checkPermission(menu.value()))
            throw new UserAuthorizationException("user not permission ");

        return true;
    }

    private void rememberLastRequest(HttpServletRequest request) {
        if (!request.getHeader("ACCEPT").contains("application/json"))
            sessionContext.set(SessionKey.stringKey(PATH), request.getRequestURI());
        // sessionContext.set(SessionKey.stringKey("ACCEPT"), request.getHeader("ACCEPT"));
    }


}
