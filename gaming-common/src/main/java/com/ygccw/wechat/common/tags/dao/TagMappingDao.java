package com.ygccw.wechat.common.tags.dao;

import com.ygccw.wechat.common.db.RsExtractor4MapList;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import core.framework.database.JPAAccess;
import core.framework.database.Query;
import core.framework.database.QueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
@Repository
public class TagMappingDao {
    @Inject
    JPAAccess jpaAccess;
    @Inject
    JdbcTemplate jdbcTemplate;


    public List<TagMapping> listByTagsId(Long tagsId, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from TagMapping").append("status", 1).append("tagsId", tagsId)
                .skipEmptyFields().orderBy("id").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public List<TagMapping> list(TagMapping tagMapping, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from TagMapping").append("status", 1)
                .append("tagType", tagMapping.getTagType()).append("tagZoneType", tagMapping.getTagZoneType()).append("entityId", tagMapping.getEntityId())
                .skipEmptyFields().orderBy("id").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public List<Map<String, Object>> listHotTags(TagMapping tagMapping, int offset, int fetchSize) {
        String sql = "select count(t1.tags_id) tag_count,t2.*,t1.entity_id from tag_mapping t1 left JOIN tags t2 on t1.tags_id=t2.id where t1.tag_type=?  group by t1.tags_id order by tag_count desc limit ?,?";
        return jdbcTemplate.query(sql, new Object[]{tagMapping.getTagType(), offset, fetchSize}, new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER}, new RsExtractor4MapList());
    }

    public int listSize(TagMapping tagMapping) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from TagMapping").append("status", 1)
                .append("tagType", tagMapping.getTagType()).append("tagZoneType", tagMapping.getTagZoneType()).append("entityId", tagMapping.getEntityId())
                .skipEmptyFields().orderBy("id").desc();
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

    public List<TagMapping> listByTagsIdPaging(Long tagsId, int offset, int fetchSize) {
        QueryBuilder queryBuilder = QueryBuilder.query("from TagMapping").append("status", 1).append("tagsId", tagsId)
                .skipEmptyFields().orderBy("id").desc();
        Query query = queryBuilder.build().from(offset).fetch(fetchSize);
        return jpaAccess.find(query);
    }

    public int listByTagsIdPagingSize(Long tagsId) {
        QueryBuilder queryBuilder = QueryBuilder.query("select count(id) from TagMapping").append("status", 1).append("tagsId", tagsId)
                .skipEmptyFields().orderBy("id").desc();
        return Integer.parseInt(jpaAccess.find(queryBuilder.build()).get(0).toString());
    }


}
