package com.ygccw.wechat.common.crawler.service;


import com.ygccw.wechat.common.crawler.entity.CrJob;
import com.ygccw.wechat.common.crawler.enums.JobType;

import java.util.List;

/**
 * @author soldier
 */
public interface CrJobService {
    void save(CrJob crJob);

    void update(CrJob crJob);

    void delete(Long id);

    CrJob findById(Long id);

    void deleteStatus(Long id);

    List<CrJob> list(JobType type);

    CrJob findByClassName(String jobClass);


}
