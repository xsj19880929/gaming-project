package com.ygccw.wechat.common.zone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
public class MatchZoneCalendar {
    @Id
    @GeneratedValue
    private Long id;
    private Long matchZoneId;
    private Long matchTeamOneId;
    private Long matchTeamTwoId;
    private String matchTeamOneName;
    private String matchTeamTwoName;
    private Date startTime;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public String getMatchTeamOneName() {
        return matchTeamOneName;
    }

    public void setMatchTeamOneName(String matchTeamOneName) {
        this.matchTeamOneName = matchTeamOneName;
    }

    public String getMatchTeamTwoName() {
        return matchTeamTwoName;
    }

    public void setMatchTeamTwoName(String matchTeamTwoName) {
        this.matchTeamTwoName = matchTeamTwoName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchZoneId() {
        return matchZoneId;
    }

    public void setMatchZoneId(Long matchZoneId) {
        this.matchZoneId = matchZoneId;
    }

    public Long getMatchTeamOneId() {
        return matchTeamOneId;
    }

    public void setMatchTeamOneId(Long matchTeamOneId) {
        this.matchTeamOneId = matchTeamOneId;
    }

    public Long getMatchTeamTwoId() {
        return matchTeamTwoId;
    }

    public void setMatchTeamTwoId(Long matchTeamTwoId) {
        this.matchTeamTwoId = matchTeamTwoId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
}
