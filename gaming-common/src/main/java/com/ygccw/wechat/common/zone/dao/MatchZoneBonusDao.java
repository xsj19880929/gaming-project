package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;
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
public class MatchZoneBonusDao {
    @Inject
    JPAAccess jpaAccess;

    public List<MatchZoneBonus> listByMatchZoneId(Long matchZoneId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from MatchZoneBonus").append("status", 1).append("matchZoneId", matchZoneId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    @Transactional
    public void save(MatchZoneBonus matchZoneBonus) {
        jpaAccess.save(matchZoneBonus);
    }

    @Transactional
    public void update(MatchZoneBonus matchZoneBonus) {
        jpaAccess.update(matchZoneBonus);
    }

    @Transactional
    public void deleteByMatchZoneId(Long matchZoneId) {
        jpaAccess.update(Query.create("delete MatchZoneBonus where matchZoneId=:matchZoneId").param("matchZoneId", matchZoneId));
    }


    public MatchZoneBonus findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from MatchZoneBonus").append("id", id).append("status", 1).build());
    }

}
