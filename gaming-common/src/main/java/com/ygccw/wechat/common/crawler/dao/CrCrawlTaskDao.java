package com.ygccw.wechat.common.crawler.dao;

import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import core.framework.database.QueryBuilderL;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author soldier
 */
@Repository
public class CrCrawlTaskDao {
    @Inject
    JPAAccess jpaAccess;

    public List<CrCrawlTask> list(String type) {
        QueryBuilderL queryBuilder = QueryBuilderL.query("from CrCrawlTask")
                .append("status", 1)
                .append("type", type)
                .skipEmptyFields();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }


    @Transactional
    public void save(CrCrawlTask crCrawlTask) {
        jpaAccess.save(crCrawlTask);
    }

    @Transactional
    public void update(CrCrawlTask crCrawlTask) {
        jpaAccess.update(crCrawlTask);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete CrCrawlTask where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update CrCrawlTask set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public CrCrawlTask findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from CrCrawlTask").append("id", id).append("status", 1).build());
    }


}
