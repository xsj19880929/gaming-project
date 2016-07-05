package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;
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
public class MatchZoneCalendarDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchZoneCalendar> listByMatchZoneId(Long matchZoneId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZoneCalendar").append("status", 1).append("matchZoneId", matchZoneId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    @Transactional
    public void save(MatchZoneCalendar matchZoneCalendar) {
        jpaAccess.save(matchZoneCalendar);
    }

    @Transactional
    public void update(MatchZoneCalendar matchZoneCalendar) {
        jpaAccess.update(matchZoneCalendar);
    }

    @Transactional
    public void deleteByMatchZoneId(Long matchZoneId) {
        jpaAccess.update(Query.create("delete MatchZoneCalendar where matchZoneId=:matchZoneId").param("matchZoneId", matchZoneId));
    }


    public MatchZoneCalendar findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchZoneCalendar").append("id", id).append("status", 1).build());
    }

}
