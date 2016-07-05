package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.SysUserRole;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author nick.guo
 */
@Component
public class SysUserRoleDao {
    @Inject
    JPAAccess jpaAccess;

    @Transactional
    public void deleteByRoleUuid(String roleUuid) {
        jpaAccess.update(Query.create("delete SysUserRole where roleUuid=:uuid").param("uuid", roleUuid));
    }

    @Transactional
    public void save(SysUserRole sysUserRole) {
        jpaAccess.save(sysUserRole);
    }

    @Transactional
    public void update(SysUserRole sysUserRole) {
        jpaAccess.update(sysUserRole);
    }

    @Transactional
    public void delete(String uuid) {
        jpaAccess.update(Query.create("delete SysUserRole where uuid=:uuid").param("uuid", uuid));
    }

    public SysUserRole findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysUserRole").append("uuid", uuid).append("status", 1).build());
    }

    public SysUserRole findBySysUserUuid(String sysUserUuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysUserRole").append("userUuid", sysUserUuid).append("status", 1)
                .build());
    }
}
