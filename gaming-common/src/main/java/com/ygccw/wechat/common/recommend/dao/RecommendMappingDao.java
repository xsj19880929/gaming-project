package com.ygccw.wechat.common.recommend.dao;

import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
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
public class RecommendMappingDao {
    @Inject
    JPAAccess jpaAccess;

    public List<RecommendMapping> listByEntityIdAndType(Long entityId, RecommendType recommendType) {
        QueryBuilder queryBuilder = QueryBuilder.query("from RecommendMapping").append("status", 1).append("entityId", entityId).append("recommendType", recommendType)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<RecommendMapping> listByRecommendIdAndType(Long recommendId, RecommendType recommendType, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from RecommendMapping").append("status", 1).append("recommendId", recommendId).append("recommendType", recommendType)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }


    public List<RecommendMapping> list(RecommendMapping recommendMapping, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from RecommendMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(RecommendMapping recommendMapping) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from RecommendMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(RecommendMapping recommendMapping) {
        jpaAccess.save(recommendMapping);
    }

    @Transactional
    public void update(RecommendMapping recommendMapping) {
        jpaAccess.update(recommendMapping);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete RecommendMapping where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update RecommendMapping set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public RecommendMapping findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from RecommendMapping").append("id", id).append("status", 1).build());
    }

}
