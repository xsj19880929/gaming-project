package com.ygccw.wechat.common.hotkeywords.dao;

import com.ygccw.wechat.common.hotkeywords.entity.HotKeywords;
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
public class HotKeywordsDao {
    @Inject
    JPAAccess jpaAccess;


    public List<HotKeywords> list(HotKeywords hotKeywords, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from HotKeywords").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(HotKeywords hotKeywords) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from HotKeywords").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(HotKeywords hotKeywords) {
        jpaAccess.save(hotKeywords);
    }

    @Transactional
    public void update(HotKeywords hotKeywords) {
        jpaAccess.update(hotKeywords);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete HotKeywords where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update HotKeywords set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public HotKeywords findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from HotKeywords").append("id", id).append("status", 1).build());
    }

}
