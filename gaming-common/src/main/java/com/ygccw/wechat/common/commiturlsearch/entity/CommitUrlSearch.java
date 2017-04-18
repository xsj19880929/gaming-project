package com.ygccw.wechat.common.commiturlsearch.entity;

import com.ygccw.wechat.common.commiturlsearch.enums.WebType;

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
public class CommitUrlSearch {
    @Id
    @GeneratedValue
    private Long id;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String url;
    @Enumerated(EnumType.STRING)
    private WebType webType;
    private Integer ifCommit;

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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebType getWebType() {
        return webType;
    }

    public void setWebType(WebType webType) {
        this.webType = webType;
    }

    public Integer getIfCommit() {
        return ifCommit;
    }

    public void setIfCommit(Integer ifCommit) {
        this.ifCommit = ifCommit;
    }
}
