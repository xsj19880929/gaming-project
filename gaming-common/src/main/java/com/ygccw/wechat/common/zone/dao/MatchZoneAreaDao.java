package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchZoneArea;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author soldier
 */
@Repository
public class MatchZoneAreaDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchZoneArea> listAll() {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZoneArea").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<MatchZoneArea> list(MatchZoneArea matchZoneArea, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZoneArea").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(MatchZoneArea matchZoneArea) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from MatchZoneArea").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(MatchZoneArea matchZoneArea) {
        jpaAccess.save(matchZoneArea);
    }

    @Transactional
    public void update(MatchZoneArea matchZoneArea) {
        jpaAccess.update(matchZoneArea);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete MatchZoneArea where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update MatchZoneArea set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public MatchZoneArea findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchZoneArea").append("id", id).append("status", 1).build());
    }

}
