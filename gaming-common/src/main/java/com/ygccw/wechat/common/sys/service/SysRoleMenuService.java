package com.ygccw.wechat.common.sys.service;

import com.ygccw.wechat.common.sys.entity.SysRoleMenu;

import java.util.List;

/**
 * @author nick.guo
 */
public interface SysRoleMenuService {
    void batchSave(List<SysRoleMenu> sysRoleMenus, String roleUuid);

    void save(SysRoleMenu sysRoleMenu);

    void update(SysRoleMenu sysRoleMenu);

    void delete(String uuid);

    SysRoleMenu findByUuid(String uuid);

    List<SysRoleMenu> findByRoleUuid(String roleUuid);

}
