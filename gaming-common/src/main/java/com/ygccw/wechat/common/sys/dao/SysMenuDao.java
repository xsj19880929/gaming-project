package com.ygccw.wechat.common.sys.dao;

import com.ygccw.wechat.common.sys.entity.SysMenu;
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
public class SysMenuDao {
    @Inject
    JPAAccess jpaAccess;

    public List<SysMenu> listByParentUuid(String parentUuid) {
        return jpaAccess.find(QueryBuilder.query(" from SysMenu").append("status", 1).append("parentUuid", parentUuid).orderBy("seq").build());
    }

    @Transactional
    public void save(SysMenu sysMenu) {
        jpaAccess.save(sysMenu);
    }

    @Transactional
    public void update(SysMenu sysMenu) {
        jpaAccess.update(sysMenu);
    }

    @Transactional
    public void delete(String uuid) {
        jpaAccess.update(Query.create("delete SysMenu where uuid=:uuid").param("uuid", uuid));
    }

    public SysMenu findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from SysMenu").append("uuid", uuid).append("status", 1).build());
    }


    public List<SysMenu> loadMenuByParentUuidAndLevel(String parentUuid, Integer level) {
        return jpaAccess.find(QueryBuilder.query(" from SysMenu").append("status", 1).append("level", level).append("parentUuid", parentUuid).skipEmptyFields().orderBy("seq").build());
    }
}
