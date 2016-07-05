package com.ygccw.wechat.constant;

import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.sys.SysMenuContext;
import core.framework.web.site.session.SessionKey;

/**
 * Created by lucian.lin on 16/5/6.
 */
public class SessionKeys {

    public static final SessionKey<SysUser> SYS_USER = SessionKey.key("sys_user", SysUser.class);
    public static final SessionKey<SysMenuContext> SYS_MENU_CONTEXT = SessionKey.key("sys_menu_context", SysMenuContext.class);

}
