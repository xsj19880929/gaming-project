package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.AnchorZonePlatform;
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
public class AnchorZonePlatformDao {
    @Inject
    JPAAccess jpaAccess;

    public List<AnchorZonePlatform> listAll() {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZonePlatform").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<AnchorZonePlatform> list(AnchorZonePlatform anchorZonePlatform, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZonePlatform").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(AnchorZonePlatform anchorZonePlatform) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from AnchorZonePlatform").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(AnchorZonePlatform anchorZonePlatform) {
        jpaAccess.save(anchorZonePlatform);
    }

    @Transactional
    public void update(AnchorZonePlatform anchorZonePlatform) {
        jpaAccess.update(anchorZonePlatform);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete AnchorZonePlatform where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update AnchorZonePlatform set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public AnchorZonePlatform findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from AnchorZonePlatform").append("id", id).append("status", 1).build());
    }

}
