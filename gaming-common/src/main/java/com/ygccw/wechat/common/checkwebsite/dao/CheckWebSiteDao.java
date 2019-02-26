package com.ygccw.wechat.common.checkwebsite.dao;

import com.ygccw.wechat.common.checkwebsite.entity.CheckWebSite;
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
public class CheckWebSiteDao {
    @Inject
    JPAAccess jpaAccess;


    public List<CheckWebSite> list(CheckWebSite checkWebSite, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from CheckWebSite").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(CheckWebSite checkWebSite) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from CheckWebSite").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(CheckWebSite checkWebSite) {
        jpaAccess.save(checkWebSite);
    }

    @Transactional
    public void update(CheckWebSite checkWebSite) {
        jpaAccess.update(checkWebSite);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete CheckWebSite where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update CheckWebSite set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public CheckWebSite findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from CheckWebSite").append("id", id).append("status", 1).build());
    }

}
