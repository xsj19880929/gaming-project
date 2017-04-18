package com.ygccw.wechat.common.crawler.service.impl;


import com.ygccw.wechat.common.crawler.dao.CrJobDao;
import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.enums.JobType;
import com.ygccw.wechat.common.crawler.service.CrJobService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class CrJobServiceImpl implements CrJobService {
    @Inject
    private CrJobDao crJobDao;

    @Override
    public void save(CrJob crJob) {
        crJob.setCreateTime(new Date());
        crJob.setUpdateTime(new Date());
        crJob.setStatus(1);
        crJobDao.save(crJob);
    }

    @Override
    public void update(CrJob crJob) {
        crJob.setUpdateTime(new Date());
        crJobDao.update(crJob);
    }

    @Override
    public void delete(Long id) {
        crJobDao.delete(id);
    }

    @Override
    public CrJob findById(Long id) {
        return crJobDao.findById(id);
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        crJobDao.deleteStatus(id);

    }

    @Override
    public List<CrJob> list(JobType type) {
        return crJobDao.list(type);
    }

    @Override
    public CrJob findByClassName(String jobClass) {
        return crJobDao.findByClassName(jobClass);
    }
}
