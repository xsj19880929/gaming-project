package com.ygccw.wechat.common.tags.dao;

import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
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
public class TagMappingDao {
    @Inject
    JPAAccess jpaAccess;

    public List<TagMapping> listByTagsId(Long tagsId) {
        QueryBuilder queryBuilder = QueryBuilder.query("from TagMapping").append("status", 1).append("tagsId", tagsId)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build();
        return jpaAccess.find(query);
    }

    public List<TagMapping> list(TagMapping tagMapping, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from TagMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listSize(TagMapping tagMapping) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from TagMapping").append("status", 1)
                .skipEmptyFields().orderBy("createTime").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());

    }

    @Transactional
    public void save(TagMapping tagMapping) {
        jpaAccess.save(tagMapping);
    }

    @Transactional
    public void update(TagMapping tagMapping) {
        jpaAccess.update(tagMapping);
    }

    @Transactional
    public void delete(Long id) {
        jpaAccess.update(Query.create("delete TagMapping where id=:id").param("id", id));
    }

    @Transactional
    public void deleteStatus(Long id) {
        jpaAccess.update(Query.create("update TagMapping set status=:status where id=:id").param("status", 0).param("id", id));
    }

    public TagMapping findById(Long id) {
        return jpaAccess.findOne(QueryBuilder.query("from TagMapping").append("id", id).append("status", 1).build());
    }

    @Transactional
    public void deleteByEntityIdAndType(Long entityId, TagType tagType, TagZoneType tagZoneType) {
        QueryBuilder queryBuilder = QueryBuilder.query("delete TagMapping").append("entityId", entityId).append("tagType", tagType).append("tagZoneType", tagZoneType).skipEmptyFields().skipNullFields();
        Query query = queryBuilder.build();
        jpaAccess.update(query);
    }


}
