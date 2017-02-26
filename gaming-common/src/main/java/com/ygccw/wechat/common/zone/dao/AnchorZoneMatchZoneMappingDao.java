package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;
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
public class AnchorZoneMatchZoneMappingDao {
    @Inject
    JPAAccess jpaAccess;

    public List<AnchorZoneMatchZoneMapping> listByAnchorZoneId(Long anchorZoneId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZoneMatchZoneMapping").append("status", 1).append("anchorZoneId", anchorZoneId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<AnchorZoneMatchZoneMapping> list(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZoneMatchZoneMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from AnchorZoneMatchZoneMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping) {
        jpaAccess.save(anchorZoneMatchZoneMapping);
    }

    @Transactional
    public void update(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping) {
        jpaAccess.update(anchorZoneMatchZoneMapping);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete AnchorZoneMatchZoneMapping where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update AnchorZoneMatchZoneMapping set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public AnchorZoneMatchZoneMapping findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from AnchorZoneMatchZoneMapping").append("id", id).append("status", 1).build());
    }

    @Transactional
    public void deleteByMatchZoneId(Long matchZoneId) {
        jpaAccess.update(Query.create("delete AnchorZoneMatchZoneMapping where matchZoneId=:matchZoneId").param("matchZoneId", matchZoneId));
    }

    @Transactional
    public void deleteByAnchorZoneId(Long anchorZoneId) {
        jpaAccess.update(Query.create("delete AnchorZoneMatchZoneMapping where anchorZoneId=:anchorZoneId").param("anchorZoneId", anchorZoneId));
    }


}
