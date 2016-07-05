package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchZoneYear;
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
public class MatchZoneYearDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchZoneYear> listAll() {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZoneYear").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<MatchZoneYear> list(MatchZoneYear matchZoneYear, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZoneYear").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(MatchZoneYear matchZoneYear) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from MatchZoneYear").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(MatchZoneYear matchZoneYear) {
        jpaAccess.save(matchZoneYear);
    }

    @Transactional
    public void update(MatchZoneYear matchZoneYear) {
        jpaAccess.update(matchZoneYear);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete MatchZoneYear where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update MatchZoneYear set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public MatchZoneYear findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchZoneYear").append("id", id).append("status", 1).build());
    }

}
