package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.common.sys.entity.SysMenu;
import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.common.sys.service.SysMenuService;
import com.ygccw.wechat.common.sys.service.impl.SecurityManager;
import com.ygccw.wechat.constant.SessionKeys;
import com.ygccw.wechat.sys.SysMenuContext;
import core.framework.web.site.session.RequireSession;
import core.framework.web.site.session.SessionContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @author nick.guo
 */
@RestController
public class MenuController {

    @Inject
    SessionContext sessionContext;

    @Inject
    SecurityManager securityManager;
    @Inject
    SysMenuService sysMenuService;

    @RequireSession
    @RequestMapping(value = "/level-1/menus", method = RequestMethod.GET)
    @ResponseBody
    public List<SysMenu> loadLevel1Menus() throws IOException {
        SysUser user = sessionContext.get(SessionKeys.SYS_USER);
        return securityManager.loadTopMenu(user);
    }

    @RequireSession
    @RequestMapping(value = "/level-2/menus", method = RequestMethod.GET)
    @ResponseBody
    public List<SysMenu> loadLevel2Menus(@RequestParam String parentUuid) throws IOException {
        SysUser user = sessionContext.get(SessionKeys.SYS_USER);
        List<SysMenu> sysMenus = securityManager.loadMenu(user, parentUuid);
        sessionContext.set(SessionKeys.SYS_MENU_CONTEXT, new SysMenuContext(sysMenus));
        return sysMenus;
    }

    @RequireSession
    @RequestMapping(value = "/sys-menu/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<SysMenu> findByRoleUuid() {
        return sysMenuService.loadMenuTree();
    }

    @RequireSession
    @RequestMapping(value = "/sys-menu", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
    }
}
