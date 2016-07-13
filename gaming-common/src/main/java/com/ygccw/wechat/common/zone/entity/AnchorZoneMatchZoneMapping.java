package com.ygccw.wechat.common.zone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
public class AnchorZoneMatchZoneMapping {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Long matchZoneId;
    private Long anchorZoneId;

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

    public Long getMatchZoneId() {
        return matchZoneId;
    }

    public void setMatchZoneId(Long matchZoneId) {
        this.matchZoneId = matchZoneId;
    }

    public Long getAnchorZoneId() {
        return anchorZoneId;
    }

    public void setAnchorZoneId(Long anchorZoneId) {
        this.anchorZoneId = anchorZoneId;
    }
}
