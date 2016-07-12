package com.ygccw.wechat.common.advertising.dao;

import com.ygccw.wechat.common.advertising.entity.Advertising;
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
public class AdvertisingDao {
    @Inject
    JPAAccess jpaAccess;
    

    public List<Advertising> list(Advertising advertising, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from Advertising").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(Advertising advertising) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from Advertising").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(Advertising advertising) {
        jpaAccess.save(advertising);
    }

    @Transactional
    public void update(Advertising advertising) {
        jpaAccess.update(advertising);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete Advertising where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update Advertising set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public Advertising findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from Advertising").append("id", id).append("status", 1).build());
    }

}
