package com.ygccw.wechat.common.sys.entity;

import com.ygccw.wechat.common.sys.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author nick.guo
 */
@Entity
@Table(name = "wc_sys_user")
public class SysUser {
    @Id
    @GeneratedValue
    private Long id;
    private String uuid;
    private String parentUuid;
    private String username;
    private String password;
    private String salt;
    private int status;
    // 关联微信公众号
    private String mpUuid;
    private String remark;
    @Enumerated(EnumType.STRING)
    private UserType userType;


    private Date createTime;
    private Date updateTime;
    // 归属公司
    private String companyUuid;
    // 关联经销商
    private String dealerUuid;

    @Transient
    private SysRole sysRole;
    @Transient
    private String parentUsername;

    public String getParentUsername() {
        return parentUsername;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getDealerUuid() {
        return dealerUuid;
    }

    public void setDealerUuid(String dealerUuid) {
        this.dealerUuid = dealerUuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
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

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getMpUuid() {
        return mpUuid;
    }

    public void setMpUuid(String mpUuid) {
        this.mpUuid = mpUuid;
    }
}
