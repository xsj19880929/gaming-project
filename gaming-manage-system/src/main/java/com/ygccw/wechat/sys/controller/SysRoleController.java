package com.ygccw.wechat.sys.controller;

import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.enums.UserType;
import com.ygccw.wechat.common.sys.service.SysRoleService;
import com.ygccw.wechat.sys.Menu;
import core.framework.database.FindResult;
import core.framework.web.site.session.RequireSession;
import core.framework.web.site.session.SessionContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author nick.guo
 */
@RestController
@Menu({"admin.sys-role.list", "company-admin.sys-role.list", "company-admin.sys-user.list", "wechat.sys-user.list"})
public class SysRoleController {

    @Inject
    SessionContext sessionContext;
    @Inject
    SysRoleService sysRoleService;


    @RequireSession
    @RequestMapping(value = "/sys-role", method = RequestMethod.GET)
    @ResponseBody
    public FindResult<SysRole> list(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value =
            "fetchSize", defaultValue = "25") int fetchSize) {
        List<SysRole> list = sysRoleService.list(offset, fetchSize);
        return new FindResult<>(list, offset, list.size());
    }

    @RequireSession
    @RequestMapping(value = "/sys-role/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public SysRole get(@PathVariable("uuid") String uuid) {
        return sysRoleService.findByUuid(uuid);
    }

    @RequireSession
    @RequestMapping(value = "/sys-role", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
    }

    @RequireSession
    @RequestMapping(value = "/sys-role", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
    }

    @RequireSession
    @RequestMapping(value = "/sys-role/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void del(@PathVariable("uuid") String uuid) {
        sysRoleService.delete(uuid);
    }


    @RequireSession
    @RequestMapping(value = "/sys-role/ByType", method = RequestMethod.GET)
    @ResponseBody
    public List<SysRole> listByType(@RequestParam UserType userType) {
        return sysRoleService.list(userType);
    }
}
