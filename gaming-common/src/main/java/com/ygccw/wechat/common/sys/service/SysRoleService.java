package com.ygccw.wechat.common.sys.service;

import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.enums.UserType;

import java.util.List;

/**
 * @author nick.guo
 */
public interface SysRoleService {
    void save(SysRole sysRole);

    void update(SysRole sysRole);

    void delete(String uuid);

    SysRole findByUuid(String uuid);

    SysRole findByName(String name);

    List<SysRole> list(int offset, int fetchSize);

    List<SysRole> list(UserType userType);
}
