package com.ygccw.wechat.common.zone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
public class MatchZoneBonus {
    @Id
    @GeneratedValue
    private Long id;
    private Long matchZoneId;
    private String ranking;
    private Double bonusFee;
    private Integer status;
    private Date createTime;
    private Date updateTime;

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

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public Double getBonusFee() {
        return bonusFee;
    }

    public void setBonusFee(Double bonusFee) {
        this.bonusFee = bonusFee;
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
