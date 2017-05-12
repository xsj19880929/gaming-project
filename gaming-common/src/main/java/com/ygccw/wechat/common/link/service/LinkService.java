package com.ygccw.wechat.common.link.service;


import com.ygccw.wechat.common.link.entity.Link;

import java.util.List;

/**
 * @author soldier
 */
public interface LinkService {
    void save(Link link);

    void update(Link link);

    void delete(Long id);

    Link findById(Long id);

    void deleteStatus(Long id);

    List<Link> list(Link link, int offset, int fetchSize);

    int listSize(Link link);

}
