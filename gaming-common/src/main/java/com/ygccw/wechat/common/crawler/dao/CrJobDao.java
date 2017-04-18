package com.ygccw.wechat.common.crawler.dao;

import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.enums.JobType;
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
public class CrJobDao {
    @Inject
    JPAAccess jpaAccess;

    public List<CrJob> list(JobType type) {
        QueryBuilderL queryBuilder = QueryBuilderL.query("from CrJob")
                .append("status", 1)
                .append("type", type)
                .skipEmptyFields();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }


    @Transactional
    public void save(CrJob crJob) {
        jpaAccess.save(crJob);
    }

    @Transactional
    public void update(CrJob crJob) {
        jpaAccess.update(crJob);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete CrJob where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update CrJob set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public CrJob findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from CrJob").append("id", id).append("status", 1).build());
    }

    public CrJob findByClassName(String jobClass) {
        return jpaAccess.findOne(QueryBuilder.query("from CrJob").append("jobClass", jobClass).append("status", 1).build());
    }


}
