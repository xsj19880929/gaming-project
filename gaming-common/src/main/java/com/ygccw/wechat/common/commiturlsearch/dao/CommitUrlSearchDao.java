package com.ygccw.wechat.common.commiturlsearch.dao;

import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.enums.WebType;
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
public class CommitUrlSearchDao {
    @Inject
    JPAAccess jpaAccess;


    public List<CommitUrlSearch> list(CommitUrlSearch commitUrlSearch, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from CommitUrlSearch").append("status", 1).append("webType", commitUrlSearch.getWebType()).append("ifCommit", commitUrlSearch.getIfCommit())
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(CommitUrlSearch commitUrlSearch) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from CommitUrlSearch").append("status", 1).append("webType", commitUrlSearch.getWebType()).append("ifCommit", commitUrlSearch.getIfCommit())
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(CommitUrlSearch commitUrlSearch) {
        jpaAccess.save(commitUrlSearch);
    }

    @Transactional
    public void update(CommitUrlSearch commitUrlSearch) {
        jpaAccess.update(commitUrlSearch);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete CommitUrlSearch where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update CommitUrlSearch set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public CommitUrlSearch findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from CommitUrlSearch").append("id", id).append("status", 1).build());
    }

    public CommitUrlSearch findByUrlAndWebType(String url, WebType webType) {
        return jpaAccess.findOne(QueryBuilder.query("from CommitUrlSearch").append("url", url).append("webType", webType).append("status", 1).build());
    }

}
