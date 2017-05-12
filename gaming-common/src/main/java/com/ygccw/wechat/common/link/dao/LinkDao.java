package com.ygccw.wechat.common.link.dao;

import com.ygccw.wechat.common.link.entity.Link;
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
public class LinkDao {
    @Inject
    JPAAccess jpaAccess;


    public List<Link> list(Link link, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from Link").append("status", 1).append("siteType", link.getSiteType())
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(Link link) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from Link").append("status", 1).append("siteType", link.getSiteType())
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(Link link) {
        jpaAccess.save(link);
    }

    @Transactional
    public void update(Link link) {
        jpaAccess.update(link);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete Link where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update Link set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public Link findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from Link").append("id", id).append("status", 1).build());
    }

}
