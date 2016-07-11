package com.ygccw.wechat.common.zone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
@Table(name = "match_team_mapping")
public class MatchTeamMapping {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Long matchZoneId;
    private Long matchTeamId;

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

    public Long getMatchTeamId() {
        return matchTeamId;
    }

    public void setMatchTeamId(Long matchTeamId) {
        this.matchTeamId = matchTeamId;
    }
}
