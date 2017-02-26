package com.ygccw.wechat.common.crawler.service.impl;


import com.ygccw.wechat.common.crawler.dao.CrCrawlTaskDao;
import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;
import com.ygccw.wechat.common.crawler.service.CrCrawlTaskService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class CrCrawlTaskServiceImpl implements CrCrawlTaskService {
    @Inject
    private CrCrawlTaskDao crCrawlTaskDao;

    @Override
    public void save(CrCrawlTask crCrawlTask) {
        crCrawlTask.setCreateTime(new Date());
        crCrawlTask.setUpdateTime(new Date());
        crCrawlTask.setStatus(1);
        crCrawlTaskDao.save(crCrawlTask);
    }

    @Override
    public void update(CrCrawlTask crCrawlTask) {
        crCrawlTask.setUpdateTime(new Date());
        crCrawlTaskDao.update(crCrawlTask);
    }

    @Override
    public void delete(Long id) {
        crCrawlTaskDao.delete(id);
    }

    @Override
    public CrCrawlTask findById(Long id) {
        return crCrawlTaskDao.findById(id);
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        crCrawlTaskDao.deleteStatus(id);

    }

    @Override
    public List<CrCrawlTask> list(String type) {
        return crCrawlTaskDao.list(type);
    }

}
