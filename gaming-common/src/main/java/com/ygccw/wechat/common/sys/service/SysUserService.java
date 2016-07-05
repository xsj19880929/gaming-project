package com.ygccw.wechat.common.sys.service;

import com.ygccw.wechat.common.sys.entity.SysUser;
import com.ygccw.wechat.common.sys.enums.UserType;

import java.util.List;

/**
 * @author nick.guo
 */
public interface SysUserService {
    SysUser authentication(SysUser sysUser);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    void resetPassword(SysUser sysUser);

    void delete(String uuid);

    SysUser findByUuid(String uuid);

    void modifyPassword(SysUser sysUser, String newPassword);

    List<SysUser> list(int offset, int fetchSize);

    List<SysUser> list(UserType userType, int offset, int fetchSize);

    List<SysUser> findByRoleUuid(String roleUuid);

    List<SysUser> findByUserType(UserType userType);

}
