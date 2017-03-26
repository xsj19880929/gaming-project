package com.ygccw.wechat.common.info.service;


import com.ygccw.wechat.common.info.entity.InfoContent;

/**
 * @author soldier
 */
public interface InfoContentService {
    void save(InfoContent infoContent);

    void update(InfoContent infoContent);

    void delete(Long infoId);

    InfoContent findByInfoId(Long infoId);

    void deleteStatus(Long infoId);

    InfoContent findByUuid(String uuid);

}
