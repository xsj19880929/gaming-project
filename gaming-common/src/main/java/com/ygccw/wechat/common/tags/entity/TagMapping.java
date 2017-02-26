package com.ygccw.wechat.common.tags.entity;

import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
public class TagMapping {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Long entityId;
    @Enumerated(EnumType.STRING)
    private TagType tagType;
    @Enumerated(EnumType.STRING)
    private TagZoneType tagZoneType;
    private Long tagsId;
    private String tagsUuid;
    private String entityUuid;
    private String uuid;

    public String getTagsUuid() {
        return tagsUuid;
    }

    public void setTagsUuid(String tagsUuid) {
        this.tagsUuid = tagsUuid;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long tagsId) {
        this.tagsId = tagsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public TagZoneType getTagZoneType() {
        return tagZoneType;
    }

    public void setTagZoneType(TagZoneType tagZoneType) {
        this.tagZoneType = tagZoneType;
    }
}
