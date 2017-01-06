package com.ygccw.wechat.common.hotkeywords.service.impl;


import com.ygccw.wechat.common.hotkeywords.dao.HotKeywordsDao;
import com.ygccw.wechat.common.hotkeywords.entity.HotKeywords;
import com.ygccw.wechat.common.hotkeywords.service.HotKeywordsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class HotKeywordServiceImpl implements HotKeywordsService {
    @Inject
    private HotKeywordsDao hotKeywordsDao;

    @Override
    public void save(HotKeywords hotKeywords) {
        hotKeywords.setCreateTime(new Date());
        hotKeywords.setUpdateTime(new Date());
        hotKeywords.setStatus(1);
        hotKeywordsDao.save(hotKeywords);
    }

    @Override
    public void update(HotKeywords hotKeywords) {
        hotKeywords.setUpdateTime(new Date());
        hotKeywordsDao.update(hotKeywords);
    }

    @Override
    public void delete(Long id) {
        hotKeywordsDao.delete(id);
    }

    @Override
    public HotKeywords findById(Long id) {
        return hotKeywordsDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        hotKeywordsDao.deleteStatus(id);
    }

    @Override
    public List<HotKeywords> list(HotKeywords hotKeywords, int offset, int fetchSize) {
        return hotKeywordsDao.list(hotKeywords, offset, fetchSize);
    }

    @Override
    public int listSize(HotKeywords hotKeywords) {
        return hotKeywordsDao.listSize(hotKeywords);
    }

}
