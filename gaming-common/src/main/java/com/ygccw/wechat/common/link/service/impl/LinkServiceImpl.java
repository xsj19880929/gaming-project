package com.ygccw.wechat.common.link.service.impl;


import com.ygccw.wechat.common.link.dao.LinkDao;
import com.ygccw.wechat.common.link.entity.Link;
import com.ygccw.wechat.common.link.service.LinkService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class LinkServiceImpl implements LinkService {
    @Inject
    private LinkDao linkDao;

    @Override
    public void save(Link link) {
        link.setCreateTime(new Date());
        link.setUpdateTime(new Date());
        link.setStatus(1);
        linkDao.save(link);
    }

    @Override
    public void update(Link link) {
        link.setUpdateTime(new Date());
        linkDao.update(link);
    }

    @Override
    public void delete(Long id) {
        linkDao.delete(id);
    }

    @Override
    public Link findById(Long id) {
        return linkDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        linkDao.deleteStatus(id);
    }

    @Override
    public List<Link> list(Link link, int offset, int fetchSize) {
        return linkDao.list(link, offset, fetchSize);
    }

    @Override
    public int listSize(Link link) {
        return linkDao.listSize(link);
    }

}
