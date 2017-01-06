package com.ygccw.wechat.common.hotkeywords.service;


import com.ygccw.wechat.common.hotkeywords.entity.HotKeywords;

import java.util.List;

/**
 * @author soldier
 */
public interface HotKeywordsService {
    void save(HotKeywords hotKeywords);

    void update(HotKeywords hotKeywords);

    void delete(Long id);

    HotKeywords findById(Long id);

    void deleteStatus(Long id);

    List<HotKeywords> list(HotKeywords hotKeywords, int offset, int fetchSize);

    int listSize(HotKeywords hotKeywords);

}
