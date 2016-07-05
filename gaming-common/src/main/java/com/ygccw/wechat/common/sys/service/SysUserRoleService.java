package com.ygccw.wechat.common.sys.service;

import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.entity.SysUserRole;

import java.util.List;

/**
 * @author nick.guo
 */
public interface SysUserRoleService {
    void batchSave(List<SysUserRole> sysUserRoles, String roleUuid);

    void save(SysUserRole sysUserRole);

    void update(SysUserRole sysUserRole);

    void delete(String uuid);

    SysUserRole findByUuid(String uuid);

    SysRole findSysRoleBySysUserUuid(String sysUserUuid);

    SysUserRole findBySysUserUuid(String sysUserUuid);
}
