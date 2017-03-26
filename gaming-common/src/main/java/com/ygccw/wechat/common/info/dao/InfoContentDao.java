package com.ygccw.wechat.common.info.dao;

import com.ygccw.wechat.common.info.entity.InfoContent;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author soldier
 */
@Repository
public class InfoContentDao {
    @Inject
    JPAAccess jpaAccess;


    @Transactional
    public void save(InfoContent infoContent) {
        jpaAccess.save(infoContent);
    }

    @Transactional
    public void update(InfoContent infoContent) {
        jpaAccess.update(infoContent);
    }

    @Transactional
    public void delete(Long infoId) {
        jpaAccess.update(Query.create("delete InfoContent where infoId=:infoId").param("infoId", infoId));
    }

    @Transactional
    public void deleteStatus(Long infoId) {
        jpaAccess.update(Query.create("update InfoContent set status=:status where infoId=:infoId").param("status", 0).param("infoId", infoId));
    }

    public InfoContent findByInfoId(Long infoId) {
        return jpaAccess.findOne(QueryBuilder.query("from InfoContent").append("infoId", infoId).append("status", 1).build());
    }

    public InfoContent findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from InfoContent").append("uuid", uuid).append("status", 1).build());
    }


}
