package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.enums.UserType;
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
public class SysRoleDao {
    @Inject
    JPAAccess jpaAccess;

    public List<SysRole> list(int offset, int fetchSize) {
        Query query = QueryBuilder.query("from SysRole").append("status", 1).orderBy("createTime").desc().build();
        query.from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    @Transactional
    public void save(SysRole sysRole) {
        jpaAccess.save(sysRole);
    }

    @Transactional
    public void update(SysRole sysRole) {
        jpaAccess.update(sysRole);
    }

    @Transactional
    public void delete(String uuid) {
        jpaAccess.update(Query.create("delete SysRole where uuid=:uuid").param("uuid", uuid));
    }

    public SysRole findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysRole").append("uuid", uuid).append("status", 1).build());
    }

    public List<SysRole> findByUserType(UserType userType) {
        return jpaAccess.find(QueryBuilder.query("from SysRole").append("userType", userType).append("status", 1).orderBy("id", true).build());
    }

    public SysRole findByName(String name) {
        return jpaAccess.findOne(QueryBuilder.query("from SysRole").append("name", name).append("status", 1).build());
    }

    public SysRole findByNameExcludeUuid(String name, String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysRole").append("name", name).append("uuid", uuid, "!=").append("status", 1).build());
    }
}
