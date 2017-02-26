package com.ygccw.wechat.common.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CrReceiveMail {
    @Id
    private String id;
    private String mail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
