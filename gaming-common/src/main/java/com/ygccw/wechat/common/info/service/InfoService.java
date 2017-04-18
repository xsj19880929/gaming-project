package com.ygccw.wechat.common.info.service;


import com.ygccw.wechat.common.info.entity.Info;

import java.util.List;

/**
 * @author soldier
 */
public interface InfoService {
    void save(Info info);

    void saveOnly(Info info);

    void update(Info info);

    void delete(Long id);

    Info findById(Long id);

    void deleteStatus(Long id);

    List<Info> list(Info info, int offset, int fetchSize);

    int listSize(Info info);

    Info lastInfo(Info info);

    Info nextInfo(Info info);

    void updateVisitCount(Long id);

    Info findByUuid(String uuid);

    Info findInfoById(Long id);

}
