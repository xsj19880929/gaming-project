package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
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
public class MatchTeamDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchTeam> listAll() {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchTeam").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<MatchTeam> list(MatchTeam matchTeam, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchTeam").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(MatchTeam matchTeam) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from MatchTeam").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(MatchTeam matchTeam) {
        jpaAccess.save(matchTeam);
    }

    @Transactional
    public void update(MatchTeam matchTeam) {
        jpaAccess.update(matchTeam);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete MatchTeam where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update MatchTeam set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public MatchTeam findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchTeam").append("id", id).append("status", 1).build());
    }

}
