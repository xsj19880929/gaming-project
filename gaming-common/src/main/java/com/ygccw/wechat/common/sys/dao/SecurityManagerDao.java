package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.SysMenu;
import core.framework.database.JPAAccess;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author nick.guo
 */
@Component
public class SecurityManagerDao {
    @Inject
    JPAAccess jpaAccess;


    public List<SysMenu> loadMenuSecurity(String userUuid, String parentUuid) {
        String sql = " select distinct m from SysUser u, SysUserRole ur, SysRoleMenu rm , SysMenu m "
            + " where 1=1 "
            + " and u.uuid=ur.userUuid "
            + " and ur.roleUuid=rm.roleUuid "
            + " and rm.menuUuid=m.uuid "
            + " and u.status=1 and ur.status=1 and rm.status=1 and m.status=1 "
            + " and u.uuid=:userUuid "
            + " and m.parentUuid=:parentUuid order by seq";

        return jpaAccess.find(core.framework.database.Query.create(sql).param("userUuid", userUuid).param("parentUuid", parentUuid));
    }

    public List<SysMenu> loadMenuSecurityByLevel(String userUuid, Integer level) {
        String sql = " select distinct m from SysUser u, SysUserRole ur, SysRoleMenu rm , SysMenu m "
            + " where 1=1 "
            + " and u.uuid=ur.userUuid "
            + " and ur.roleUuid=rm.roleUuid "
            + " and rm.menuUuid=m.uuid "
            + " and u.status=1 and ur.status=1 and rm.status=1 and m.status=1 "
            + " and u.uuid=:userUuid "
            + " and m.level=:level order by seq";

        return jpaAccess.find(core.framework.database.Query.create(sql).param("userUuid", userUuid).param("level", level));
    }




}
