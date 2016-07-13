package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchZone;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import core.framework.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author soldier
 */
@Repository
public class MatchZoneDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchZone> list(MatchZone matchZone, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZone").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        if (StringUtils.hasText(matchZone.getName())) {
            queryBuilder.append("name", "%" + matchZone.getName() + "%", "like");
        }
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(MatchZone matchZone) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from MatchZone").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        if (StringUtils.hasText(matchZone.getName())) {
            queryBuilder.append("name", "%" + matchZone.getName() + "%", "like");
        }
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(MatchZone matchZone) {
        jpaAccess.save(matchZone);
    }

    @Transactional
    public void update(MatchZone matchZone) {
        jpaAccess.update(matchZone);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete MatchZone where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update MatchZone set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public MatchZone findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchZone").append("id", id).append("status", 1).build());
    }

    public List<MatchZone> listAll() {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZone").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<MatchZone> listByIfAnchorMatch() {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZone").append("status", 1).append("ifAnchorMatch", true)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

}
