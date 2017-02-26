package com.ygccw.wechat.common.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CrCrawlTaskLog {
    public static final Integer ISSUCCESS_YES = 0;
    public static final Integer ISSUCCESS_NO = -1;
    @Id
    private String id;
    private String type;
    private String exceptionMsg;
    private String url;
    private String crawlerTaskId;
    private Integer isSuccess;
    private Date createdTime;
    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCrawlerTaskId() {
        return crawlerTaskId;
    }

    public void setCrawlerTaskId(String crawlerTaskId) {
        this.crawlerTaskId = crawlerTaskId;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
