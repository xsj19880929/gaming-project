package com.ygccw.wechat.common.picture.dao;

import com.ygccw.wechat.common.picture.entity.Picture;
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
public class PictureDao {
    @Inject
    JPAAccess jpaAccess;

    public List<Picture> list(Picture picture, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from Picture")
                .append("status", 1)
                .append("zoneId", picture.getZoneId())
                .append("pictureZoneType", picture.getPictureZoneType())
                .append("verify", picture.getVerify())
                .skipEmptyFields();
        if (StringUtils.hasText(picture.getDescription())) {
            queryBuilder.append("description", "%" + picture.getDescription() + "%", "like");
        }
        if (StringUtils.hasText(picture.getSortName())) {
            queryBuilder.orderBy(picture.getSortName(), picture.getSortIfDesc());
        } else {
            queryBuilder.orderBy("updateTime", true);
        }
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(Picture picture) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from Picture")
                .append("status", 1)
                .append("zoneId", picture.getZoneId())
                .append("pictureZoneType", picture.getPictureZoneType())
                .append("verify", picture.getVerify())
                .skipEmptyFields();
        if (StringUtils.hasText(picture.getDescription())) {
            queryBuilder.append("description", "%" + picture.getDescription() + "%", "like");
        }
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(Picture picture) {
        jpaAccess.save(picture);
    }

    @Transactional
    public void update(Picture picture) {
        jpaAccess.update(picture);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete Picture where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update Picture set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public Picture findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from Picture").append("id", id).append("status", 1).build());
    }

    @Transactional
    public void updateVisitCount(Long id) {
        jpaAccess.update(Query.create("update Picture set visitCount=visitCount+1 where id=:id").param("id", id));
    }

}
