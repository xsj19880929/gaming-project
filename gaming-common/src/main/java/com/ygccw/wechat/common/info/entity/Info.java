package com.ygccw.wechat.common.info.entity;

import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Entity
public class Info {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Long zoneId;
    @Enumerated(EnumType.STRING)
    private InfoZoneType infoZoneType;
    @Enumerated(EnumType.STRING)
    private InfoType infoType;
    @Enumerated(EnumType.STRING)
    private InfoVideoType infoVideoType;
    private String title;
    private String subTitle;
    private Integer visitCount;
    private String titleImage;
    private String content;
    private String seoTitle;
    private String seoKeywords;
    private String seoDescription;
    private String tags;
    private Integer verify;
    private String source;
    private Date publishTime;
    private String author;
    @Transient
    private List<Long> zoneIdList;
    @Transient
    private String sortName;
    @Transient
    private Boolean sortIfDesc;
    private String zoneUuid;
    private String uuid;
    private String webSite;

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getZoneUuid() {
        return zoneUuid;
    }

    public void setZoneUuid(String zoneUuid) {
        this.zoneUuid = zoneUuid;
    }

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

    public List<Long> getZoneIdList() {
        return zoneIdList;
    }

    public void setZoneIdList(List<Long> zoneIdList) {
        this.zoneIdList = zoneIdList;
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

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public InfoZoneType getInfoZoneType() {
        return infoZoneType;
    }

    public void setInfoZoneType(InfoZoneType infoZoneType) {
        this.infoZoneType = infoZoneType;
    }

    public InfoType getInfoType() {
        return infoType;
    }

    public void setInfoType(InfoType infoType) {
        this.infoType = infoType;
    }

    public InfoVideoType getInfoVideoType() {
        return infoVideoType;
    }

    public void setInfoVideoType(InfoVideoType infoVideoType) {
        this.infoVideoType = infoVideoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
