package com.ygccw.wechat.common.commiturlsearch.service.impl;


import com.ygccw.wechat.common.commiturlsearch.dao.CommitUrlSearchDao;
import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.enums.WebType;
import com.ygccw.wechat.common.commiturlsearch.service.CommitUrlSearchService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class CommitUrlSearchServiceImpl implements CommitUrlSearchService {
    @Inject
    private CommitUrlSearchDao commitUrlSearchDao;

    @Override
    public void save(CommitUrlSearch commitUrlSearch) {
        commitUrlSearch.setCreateTime(new Date());
        commitUrlSearch.setUpdateTime(new Date());
        commitUrlSearch.setStatus(1);
        commitUrlSearch.setIfCommit(0);
        commitUrlSearchDao.save(commitUrlSearch);
    }

    @Override
    public void update(CommitUrlSearch commitUrlSearch) {
        commitUrlSearch.setUpdateTime(new Date());
        commitUrlSearchDao.update(commitUrlSearch);
    }

    @Override
    public void delete(Long id) {
        commitUrlSearchDao.delete(id);
    }

    @Override
    public CommitUrlSearch findById(Long id) {
        return commitUrlSearchDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        commitUrlSearchDao.deleteStatus(id);
    }

    @Override
    public List<CommitUrlSearch> list(CommitUrlSearch commitUrlSearch, int offset, int fetchSize) {
        return commitUrlSearchDao.list(commitUrlSearch, offset, fetchSize);
    }

    @Override
    public int listSize(CommitUrlSearch commitUrlSearch) {
        return commitUrlSearchDao.listSize(commitUrlSearch);
    }

    @Override
    public CommitUrlSearch findByUrlAndWebType(String url, WebType webType) {
        return commitUrlSearchDao.findByUrlAndWebType(url, webType);
    }
}
