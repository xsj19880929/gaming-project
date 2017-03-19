package com.ygccw.wechat.common.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CrCrawlTask {
    @Id
    private Long id;
    private String url;
    private String templeName;
    private Integer level;
    private Integer status;
    private String type;
    private Date createTime;
    private Date updateTime;
    private String classify;
    private String infoZoneType;
    private String infoType;
    private String zoneUuid;
    private String infoVideoType;
    private String lastUrl;
    private String htmlCharset;

    public String getHtmlCharset() {
        return htmlCharset;
    }

    public void setHtmlCharset(String htmlCharset) {
        this.htmlCharset = htmlCharset;
    }

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }

    public String getInfoZoneType() {
        return infoZoneType;
    }

    public void setInfoZoneType(String infoZoneType) {
        this.infoZoneType = infoZoneType;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getZoneUuid() {
        return zoneUuid;
    }

    public void setZoneUuid(String zoneUuid) {
        this.zoneUuid = zoneUuid;
    }

    public String getInfoVideoType() {
        return infoVideoType;
    }

    public void setInfoVideoType(String infoVideoType) {
        this.infoVideoType = infoVideoType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
}
