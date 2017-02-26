package com.ygccw.wechat.common.crawler.service;


import com.ygccw.wechat.common.crawler.entity.CrCrawlTask;

import java.util.List;

/**
 * @author soldier
 */
public interface CrCrawlTaskService {
    void save(CrCrawlTask crCrawlTask);

    void update(CrCrawlTask crCrawlTask);

    void delete(Long id);

    CrCrawlTask findById(Long id);

    void deleteStatus(Long id);

    List<CrCrawlTask> list(String type);


}
