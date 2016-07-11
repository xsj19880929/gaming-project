package com.ygccw.wechat.common.recommend.dao;

import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
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
public class RecommendDao {
    @Inject
    JPAAccess jpaAccess;

    public List<Recommend> listByType(RecommendType recommendType) {
        QueryBuilder queryBuilder = QueryBuilder.query("from Recommend").append("status", 1).append("recommendType", recommendType)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<Recommend> list(Recommend recommend, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from Recommend").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(Recommend recommend) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from Recommend").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(Recommend recommend) {
        jpaAccess.save(recommend);
    }

    @Transactional
    public void update(Recommend recommend) {
        jpaAccess.update(recommend);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete Recommend where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update Recommend set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public Recommend findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from Recommend").append("id", id).append("status", 1).build());
    }

}
