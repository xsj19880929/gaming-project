package com.ygccw.wechat.common.sys.service;

import com.ygccw.wechat.common.sys.entity.SysMenu;

import java.util.List;

/**
 * @author nick.guo
 */
public interface SysMenuService {
    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void delete(String uuid);

    SysMenu findByUuid(String uuid);

    List<SysMenu> loadMenuTree();

    List<SysMenu> loadMenuByParentUuidAndLevel(String parentUuid, Integer level);
}
