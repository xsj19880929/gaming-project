package com.ygccw.wechat.common.advertising.service;


import com.ygccw.wechat.common.advertising.entity.Advertising;
import com.ygccw.wechat.common.advertising.enums.AdvType;

import java.util.List;

/**
 * @author soldier
 */
public interface AdvertisingService {
    void save(Advertising advertising);

    void update(Advertising advertising);

    void delete(Long id);

    Advertising findById(Long id);

    void deleteStatus(Long id);

    List<Advertising> list(Advertising advertising, int offset, int fetchSize);

    int listSize(Advertising advertising);

    Advertising findByAdvType(AdvType advType);
}
