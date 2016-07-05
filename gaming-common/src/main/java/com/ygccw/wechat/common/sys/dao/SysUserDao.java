package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.SysUser;
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
public class SysUserDao {
    @Inject
    JPAAccess jpaAccess;

    public List<SysUser> list(int offset, int fetchSize) {
        return this.list(null, offset, fetchSize);
    }

    public List<SysUser> list(UserType userType, int offset, int fetchSize) {
        Query query = QueryBuilder.query("from SysUser").append("status", 1).append("userType", userType)
            .skipEmptyFields().orderBy("createTime").desc().build();
        query.from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public List<SysUser> findByRoleUuid(String roleUuid) {
        return jpaAccess.find(Query.create(" select u from SysUser u , SysRole r,SysUserRole ur where u.uuid=ur.userUuid "
            + "and r.uuid=ur.roleUuid and r.uuid=:roleUuid").param("roleUuid", roleUuid));

    }


    public List<SysUser> findByUserType(UserType userType) {
        return jpaAccess.find(QueryBuilder.query(" from SysUser ").append("status", 1).append("userType", userType)
            .orderBy("id", true).build());
    }

    @Transactional
    public void save(SysUser sysUser) {
        jpaAccess.save(sysUser);
    }

    @Transactional
    public void update(SysUser sysUser) {
        jpaAccess.update(sysUser);
    }

    @Transactional
    public void delete(String uuid) {
        jpaAccess.update(Query.create("delete SysUser where uuid=:uuid").param("uuid", uuid));
    }

    public SysUser findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysUser").append("uuid", uuid).append("status", 1).build());
    }

    public SysUser findByUsername(String username) {
        return jpaAccess.findOne(QueryBuilder.query("from SysUser").append("username", username).append("status", 1)
            .build());
    }

}
