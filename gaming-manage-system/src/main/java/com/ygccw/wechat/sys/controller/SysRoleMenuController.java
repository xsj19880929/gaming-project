package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.common.sys.entity.SysRoleMenu;
import com.ygccw.wechat.common.sys.service.SysRoleMenuService;
import core.framework.web.site.session.RequireSession;
import core.framework.web.site.session.SessionContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author nick.guo
 */
@RestController
public class SysRoleMenuController {

    @Inject
    SessionContext sessionContext;
    @Inject
    SysRoleMenuService sysRoleMenuService;

    @RequireSession
    @RequestMapping(value = "/sys-role-menu/batch/{roleUuid}", method = RequestMethod.POST)
    @ResponseBody
    public void batchSave(@Valid @RequestBody List<SysRoleMenu> sysRoleMenus, @PathVariable String roleUuid) {
        sysRoleMenuService.batchSave(sysRoleMenus, roleUuid);
    }

    @RequireSession
    @RequestMapping(value = "/sys-role-menu/findByRoleUuid/{roleUuid}", method = RequestMethod.GET)
    @ResponseBody
    public List<SysRoleMenu> findByUuid(@PathVariable String roleUuid) {
        return sysRoleMenuService.findByRoleUuid(roleUuid);
    }

}
