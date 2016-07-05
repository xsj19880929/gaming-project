package com.ygccw.wechat.sys.controller;

import com.google.common.base.Strings;
import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.common.sys.entity.SysUserRole;
import com.ygccw.wechat.common.sys.enums.UserType;
import com.ygccw.wechat.common.sys.service.SysRoleService;
import com.ygccw.wechat.common.sys.service.SysUserRoleService;
import com.ygccw.wechat.common.sys.service.SysUserService;
import com.ygccw.wechat.constant.SessionKeys;
import com.ygccw.wechat.sys.Menu;
import com.ygccw.wechat.sys.utils.UserTypeRouter;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nick.guo
 */
@RestController
@Menu({"admin.sys-user.list", "company-admin.sys-user.list", "wechat.sys-user.list"})
public class SysUserController {

    @Inject
    SessionContext sessionContext;
    @Inject
    SysUserService sysUserService;
    @Inject
    SysRoleService sysRoleService;
    @Inject
    SysUserRoleService sysUserRoleService;


    @RequireSession
    @RequestMapping(value = "/user/modify-password", method = RequestMethod.POST)
    public void modifyPassword(HttpServletResponse response, @Valid @RequestParam String password, @Valid @RequestParam String username, @Valid @RequestParam String newPassword) throws IOException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUserService.modifyPassword(sysUser, newPassword);
        response.getWriter().write("success");
    }

    @RequireSession
    @RequestMapping(value = "/sys-user", method = RequestMethod.GET)
    @ResponseBody
    public FindResult<SysUser> list(@RequestParam(required = false) String roleUuid, @RequestParam(value = "offset",
        defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        SysRole sysRole = null;
        if (!Strings.isNullOrEmpty(roleUuid)) {
            sysRole = sysRoleService.findByUuid(roleUuid);
        }
        List<SysUser> list = sysUserService.list(null == sysRole ? null : sysRole.getUserType(), offset, fetchSize);
        setParentUsername(list);

        return new FindResult<>(list, offset, list.size());
    }

    /**
     * 用于公司管理员界面入口,只能查看到经销商type类型到账户
     *
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequireSession
    @RequestMapping(value = "/sys-user/distributor", method = RequestMethod.GET)
    @ResponseBody
    public FindResult<SysUser> listByDistributorType(@RequestParam(value = "offset",
        defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {

        List<SysUser> list = sysUserService.list(UserType.DISTRIBUTOR, offset, fetchSize);
        setParentUsername(list);

        return new FindResult<>(list, offset, list.size());
    }

    @RequireSession
    @RequestMapping(value = "/sys-user/staff", method = RequestMethod.GET)
    @ResponseBody
    public FindResult<SysUser> listByStaffType(@RequestParam(value = "offset",
        defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {

        List<SysUser> list = sysUserService.list(UserType.STAFF, offset, fetchSize);
        setParentUsername(list);
        return new FindResult<>(list, offset, list.size());
    }

    @RequireSession
    @RequestMapping(value = "/sys-user/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public SysUser get(@PathVariable("uuid") String uuid) {
        SysUser sysUser = sysUserService.findByUuid(uuid);
        sysUser.setSysRole(sysUserRoleService.findSysRoleBySysUserUuid(uuid));
        sysUser.setPassword(null);
        return sysUser;
    }

    @RequireSession
    @RequestMapping(value = "/sys-user", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody SysUser sysUser) {
        if (Strings.isNullOrEmpty(sysUser.getParentUuid())) {
            SysUser currentUser = sessionContext.get(SessionKeys.SYS_USER);
            sysUser.setParentUuid(currentUser.getUuid());
        }

        sysUserService.save(sysUser);
        if (null != sysUser.getSysRole()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleUuid(sysUser.getSysRole().getUuid());
            sysUserRole.setUserUuid(sysUser.getUuid());
            sysUserRoleService.save(sysUserRole);
        }
    }

    @RequireSession
    @RequestMapping(value = "/sys-user", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
    }

    @RequireSession
    @RequestMapping(value = "/sys-user/reset-password", method = RequestMethod.PUT)
    @ResponseBody
    public void resetPassword(@RequestBody SysUser sysUser) {
        sysUserService.resetPassword(sysUser);
    }

    @RequireSession
    @RequestMapping(value = "/sys-user/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void del(@PathVariable("uuid") String uuid) {
        sysUserService.delete(uuid);
    }


    @RequireSession
    @RequestMapping(value = "/sys-user/by-role-uuid/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public List<SysUser> findByRoleUuid(@PathVariable("uuid") String roleUuid) {
        return sysUserService.findByRoleUuid(roleUuid);
    }

    /**
     * 用于账户类型创建规则
     *
     * @param userType
     * @return
     */
    @RequireSession
    @RequestMapping(value = "/sys-user/router/user-type", method = RequestMethod.GET)
    @ResponseBody
    public List<SysUser> findByUserType(@RequestParam UserType userType) {
        List<SysUser> list = new ArrayList<>();
        UserType parentType = UserTypeRouter.routerUp(userType);
        if (null == parentType) {
            list.add(sessionContext.get(SessionKeys.SYS_USER));
            return list;
        }
        return sysUserService.findByUserType(parentType);
    }

    private void setParentUsername(List<SysUser> list) {
        for (SysUser sysUser : list) {
            if (null == sysUser.getParentUuid()) continue;
            SysUser parent = sysUserService.findByUuid(sysUser.getParentUuid());
            if (null != parent) sysUser.setParentUsername(parent.getUsername());
        }
    }
}
