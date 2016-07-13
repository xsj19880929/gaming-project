package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.AnchorZoneHonor;
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
public class AnchorZoneHonorDao {
    @Inject
    JPAAccess jpaAccess;

    public List<AnchorZoneHonor> listByAnchorZoneId(Long anchorZoneId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZoneHonor").append("status", 1).append("anchorZoneId", anchorZoneId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    @Transactional
    public void save(AnchorZoneHonor anchorZoneHonor) {
        jpaAccess.save(anchorZoneHonor);
    }

    @Transactional
    public void update(AnchorZoneHonor anchorZoneHonor) {
        jpaAccess.update(anchorZoneHonor);
    }

    @Transactional
    public void deleteByAnchorZoneId(Long anchorZoneId) {
        jpaAccess.update(Query.create("delete AnchorZoneHonor where anchorZoneId=:anchorZoneId").param("anchorZoneId", anchorZoneId));
    }


    public AnchorZoneHonor findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from AnchorZoneHonor").append("id", id).append("status", 1).build());
    }

}
