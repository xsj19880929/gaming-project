package com.ygccw.wechat.common.sys.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lucian.lin on 16/5/25.
 * 系统登陆日志
 */

@Entity
@Table(name = "wc_sys_user_login")
public class SysUserLogin {
    @Id
    @GeneratedValue
    private Long id;
    private String uuid;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String mpUuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getMpUuid() {
        return mpUuid;
    }

    public void setMpUuid(String mpUuid) {
        this.mpUuid = mpUuid;
    }
}