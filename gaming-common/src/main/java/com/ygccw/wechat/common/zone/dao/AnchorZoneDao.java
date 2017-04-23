package com.ygccw.wechat.common.zone.dao;

import com.ygccw.wechat.common.zone.entity.AnchorZone;
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
public class AnchorZoneDao {
    @Inject
    JPAAccess jpaAccess;

    public List<AnchorZone> list(AnchorZone anchorZone, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZone").append("status", 1)
                .append("createTime", anchorZone.getCreateStartTime(), "startCreateTime", ">=")
                .append("createTime", anchorZone.getCreateEndTime(), "endCreateTime", "<=")
                .skipEmptyFields().append("platformId", anchorZone.getPlatformId());
        if (StringUtils.hasText(anchorZone.getName())) {
            queryBuilder.append("name", "%" + anchorZone.getName() + "%", "like");
        }
        if (StringUtils.hasText(anchorZone.getSortName())) {
            queryBuilder.orderBy(anchorZone.getSortName(), anchorZone.getSortIfDesc());
        } else {
            queryBuilder.orderBy("updateTime", true);
        }
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(AnchorZone anchorZone) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from AnchorZone").append("status", 1)
                .skipEmptyFields().append("platformId", anchorZone.getPlatformId());
        if (StringUtils.hasText(anchorZone.getName())) {
            queryBuilder.append("name", "%" + anchorZone.getName() + "%", "like");
        }
        if (StringUtils.hasText(anchorZone.getSortName())) {
            queryBuilder.orderBy(anchorZone.getSortName(), anchorZone.getSortIfDesc());
        } else {
            queryBuilder.orderBy("updateTime", true);
        }
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(AnchorZone anchorZone) {
        jpaAccess.save(anchorZone);
    }

    @Transactional
    public void update(AnchorZone anchorZone) {
        jpaAccess.update(anchorZone);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete AnchorZone where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update AnchorZone set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public AnchorZone findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from AnchorZone").append("id", id).append("status", 1).build());
    }

    public List<AnchorZone> listAll() {
        QueryBuilder queryBuilder = QueryBuilder.query("from AnchorZone").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    @Transactional
    public void updateVisitCount(Long id) {
        jpaAccess.update(Query.create("update AnchorZone set visitCount=visitCount+1 where id=:id").param("id", id));
    }

    public AnchorZone findByUuid(String uuid) {
        return jpaAccess.findOne(QueryBuilder.query("from AnchorZone").append("uuid", uuid).append("status", 1).build());
    }

}
