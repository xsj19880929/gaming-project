package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.common.sys.entity.SysUserRole;
import com.ygccw.wechat.common.sys.service.SysRoleService;
import com.ygccw.wechat.common.sys.service.SysUserRoleService;
import com.ygccw.wechat.common.sys.service.SysUserService;
import com.ygccw.wechat.sys.Menu;
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
@Menu({"admin.sys-role.list", "company-admin.sys-role.list", "company-admin.sys-user.list", "wechat.sys-user.list"})
public class SysUserRoleController {

    @Inject
    SessionContext sessionContext;
    @Inject
    SysRoleService sysRoleService;
    @Inject
    SysUserRoleService sysUserRoleService;
    @Inject
    SysUserService sysUserService;


    @RequireSession
    @RequestMapping(value = "/sys-user-role/batch/{roleUuid}", method = RequestMethod.POST)
    @ResponseBody
    public void batchSave(@Valid @RequestBody List<SysUserRole> userRoles, @PathVariable String roleUuid) {
        sysUserRoleService.batchSave(userRoles, roleUuid);
    }

    @RequireSession
    @RequestMapping(value = "/sys-user-role/sysUserUuid/{sysUserUuid}", method = RequestMethod.GET)
    @ResponseBody
    public SysUserRole findBySysUserUuid(@PathVariable String sysUserUuid) {
        return sysUserRoleService.findBySysUserUuid(sysUserUuid);
    }

    @RequireSession
    @RequestMapping(value = "/sys-user-role", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@Valid @RequestBody SysUserRole sysUserRole) {
        sysUserRoleService.update(sysUserRole);
    }
}
