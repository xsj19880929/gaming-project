package com.ygccw.wechat.common.info.dao;

import com.ygccw.wechat.common.info.entity.Info;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import core.framework.database.QueryBuilderL;
import core.framework.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author soldier
 */
@Repository
public class InfoDao {
    @Inject
    JPAAccess jpaAccess;

    public List<Info> list(Info info, int offset, int fetchSize) {
        QueryBuilderL queryBuilder = QueryBuilderL.query("from Info")
                .append("status", 1)
                .append("zoneId", info.getZoneId())
                .append("infoZoneType", info.getInfoZoneType())
                .append("infoType", info.getInfoType())
                .append("infoVideoType", info.getInfoVideoType())
                .append("verify", info.getVerify())
                .append("zoneId", info.getZoneIdList(), "zoneId", "=", "or")
                .skipEmptyFields().orderBy("updateTime").desc();
        if (StringUtils.hasText(info.getTitle())) {
            queryBuilder.append("title", "%" + info.getTitle() + "%", "like");
        }
        if (StringUtils.hasText(info.getSortName())) {
            queryBuilder.orderBy(info.getSortName(), info.getSortIfDesc());
        } else {
            queryBuilder.orderBy("updateTime", true);
        }
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(Info info) {
        QueryBuilderL queryBuilder = QueryBuilderL.query("select count(id) from Info")
                .append("status", 1)
                .append("zoneId", info.getZoneId())
                .append("infoZoneType", info.getInfoZoneType())
                .append("infoType", info.getInfoType())
                .append("infoVideoType", info.getInfoVideoType())
                .append("verify", info.getVerify())
                .append("zoneId", info.getZoneIdList(), "zoneId", "=", "or")
                .skipEmptyFields();
        if (StringUtils.hasText(info.getTitle())) {
            queryBuilder.append("title", "%" + info.getTitle() + "%", "like");
        }
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(Info info) {
        jpaAccess.save(info);
    }

    @Transactional
    public void update(Info info) {
        jpaAccess.update(info);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete Info where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update Info set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public Info findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from Info").append("id", id).append("status", 1).build());
    }

}
