package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
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
public class MatchTeamMappingDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchTeamMapping> listByMatchTeamId(Long matchTeamId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchTeamMapping").append("status", 1).append("matchTeamId", matchTeamId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<MatchTeamMapping> list(MatchTeamMapping matchTeamMapping, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchTeamMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(MatchTeamMapping matchTeamMapping) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from MatchTeamMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(MatchTeamMapping matchTeamMapping) {
        jpaAccess.save(matchTeamMapping);
    }

    @Transactional
    public void update(MatchTeamMapping matchTeamMapping) {
        jpaAccess.update(matchTeamMapping);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete MatchTeamMapping where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update MatchTeamMapping set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public MatchTeamMapping findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchTeamMapping").append("id", id).append("status", 1).build());
    }

}
