package com.ygccw.wechat.common.zone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
public class AnchorZoneHonor {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String name;
    private Long anchorZoneId;
    private Date honorTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAnchorZoneId() {
        return anchorZoneId;
    }

    public void setAnchorZoneId(Long anchorZoneId) {
        this.anchorZoneId = anchorZoneId;
    }

    public Date getHonorTime() {
        return honorTime;
    }

    public void setHonorTime(Date honorTime) {
        this.honorTime = honorTime;
    }
}
