package com.ygccw.wechat.common.picture.dao;

import com.ygccw.wechat.common.picture.entity.PictureDetail;
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
public class PictureDetailDao {
    @Inject
    JPAAccess jpaAccess;

    public List<PictureDetail> listByPictureId(Long pictureId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from PictureDetail").append("status", 1).append("pictureId", pictureId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    @Transactional
    public void save(PictureDetail pictureDetail) {
        jpaAccess.save(pictureDetail);
    }

    @Transactional
    public void update(PictureDetail pictureDetail) {
        jpaAccess.update(pictureDetail);
    }

    @Transactional
    public void deleteByPictureId(Long pictureId) {
        jpaAccess.update(Query.create("delete PictureDetail where pictureId=:pictureId").param("pictureId", pictureId));
    }


    public PictureDetail findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from PictureDetail").append("id", id).append("status", 1).build());
    }

}
