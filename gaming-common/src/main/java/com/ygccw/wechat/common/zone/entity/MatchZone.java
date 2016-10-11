package com.ygccw.wechat.common.zone.entity;

import com.ygccw.wechat.common.zone.enums.MatchStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author soldier
 */
@Entity
@Table(name = "match_zone")
public class MatchZone {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String name;
    private String nameSpell;
    private String nameFirstLetter;
    private Long matchZoneAreaId;
    private String matchZoneAreaName;
    private Long matchZoneYearId;
    private String matchZoneYearName;
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    private String introduction;
    private String seoTitle;
    private String seoKeywords;
    private String seoDescription;
    private Long visitCount;
    private String icoImage;
    private String backgroundImage;
    private Date startTime;
    private Date endTime;
    private String matchType;
    private String hostParty;
    private Boolean ifAnchorMatch;
    private Boolean ifStart;
    @Transient
    private String sortName;
    @Transient
    private Boolean sortIfDesc;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Boolean getSortIfDesc() {
        return sortIfDesc;
    }

    public void setSortIfDesc(Boolean sortIfDesc) {
        this.sortIfDesc = sortIfDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpell() {
        return nameSpell;
    }

    public void setNameSpell(String nameSpell) {
        this.nameSpell = nameSpell;
    }

    public String getNameFirstLetter() {
        return nameFirstLetter;
    }

    public void setNameFirstLetter(String nameFirstLetter) {
        this.nameFirstLetter = nameFirstLetter;
    }

    public Long getMatchZoneAreaId() {
        return matchZoneAreaId;
    }

    public void setMatchZoneAreaId(Long matchZoneAreaId) {
        this.matchZoneAreaId = matchZoneAreaId;
    }

    public String getMatchZoneAreaName() {
        return matchZoneAreaName;
    }

    public void setMatchZoneAreaName(String matchZoneAreaName) {
        this.matchZoneAreaName = matchZoneAreaName;
    }

    public Long getMatchZoneYearId() {
        return matchZoneYearId;
    }

    public void setMatchZoneYearId(Long matchZoneYearId) {
        this.matchZoneYearId = matchZoneYearId;
    }

    public String getMatchZoneYearName() {
        return matchZoneYearName;
    }

    public void setMatchZoneYearName(String matchZoneYearName) {
        this.matchZoneYearName = matchZoneYearName;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public String getIcoImage() {
        return icoImage;
    }

    public void setIcoImage(String icoImage) {
        this.icoImage = icoImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getHostParty() {
        return hostParty;
    }

    public void setHostParty(String hostParty) {
        this.hostParty = hostParty;
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

    public Boolean getIfAnchorMatch() {
        return ifAnchorMatch;
    }

    public void setIfAnchorMatch(Boolean ifAnchorMatch) {
        this.ifAnchorMatch = ifAnchorMatch;
    }

    public Boolean getIfStart() {
        return ifStart;
    }

    public void setIfStart(Boolean ifStart) {
        this.ifStart = ifStart;
    }
}
