package com.ygccw.wechat.common.tags.dao;

import com.ygccw.wechat.common.tags.entity.Tags;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import core.framework.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author soldier
 */
@Repository
public class TagsDao {
    @Inject
    JPAAccess jpaAccess;

    public List<Tags> list(Tags tags, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from Tags")
                .append("status", 1)
                .skipEmptyFields().orderBy("updateTime").desc();
        if (StringUtils.hasText(tags.getName())) {
            queryBuilder.append("name", "%" + tags.getName() + "%", "like");
        }
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(Tags tags) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from Tags")
                .append("status", 1)
                .skipEmptyFields();
        if (StringUtils.hasText(tags.getName())) {
            queryBuilder.append("name", "%" + tags.getName() + "%", "like");
        }
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(Tags tags) {
        jpaAccess.save(tags);
    }

    @Transactional
    public void update(Tags tags) {
        jpaAccess.update(tags);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete Tags where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update Tags set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public Tags findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from Tags").append("id", id).append("status", 1).build());
    }

}
