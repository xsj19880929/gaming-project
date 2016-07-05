package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.SysRoleMenu;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author nick.guo
 */
@Component
public class SysRoleMenuDao {
    @Inject
    JPAAccess jpaAccess;

    @Transactional
    public void deleteByRoleUuid(String roleUuid) {
        jpaAccess.update(Query.create("delete SysRoleMenu where roleUuid=:uuid").param("uuid", roleUuid));
    }

    @Transactional
    public void save(SysRoleMenu sysRoleMenu) {
        jpaAccess.save(sysRoleMenu);
    }

    @Transactional
    public void update(SysRoleMenu sysRoleMenu) {
        jpaAccess.update(sysRoleMenu);
    }

    @Transactional
    public void delete(String uuid) {
        jpaAccess.update(Query.create("delete SysRoleMenu where uuid=:uuid").param("uuid", uuid));
    }

    public SysRoleMenu findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysRoleMenu").append("uuid", uuid).append("status", 1).build());
    }

    public List<SysRoleMenu> findByRoleUuid(String roleUuid) {
        return jpaAccess.find(QueryBuilder.query("from SysRoleMenu").append("roleUuid", roleUuid).append("status", 1)
                .build());
    }
}
