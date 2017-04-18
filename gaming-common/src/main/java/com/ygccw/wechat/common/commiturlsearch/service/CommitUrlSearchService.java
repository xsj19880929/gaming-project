package com.ygccw.wechat.common.commiturlsearch.service;


import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.enums.WebType;

import java.util.List;

/**
 * @author soldier
 */
public interface CommitUrlSearchService {
    void save(CommitUrlSearch commitUrlSearch);

    void update(CommitUrlSearch commitUrlSearch);

    void delete(Long id);

    CommitUrlSearch findById(Long id);

    void deleteStatus(Long id);

    List<CommitUrlSearch> list(CommitUrlSearch commitUrlSearch, int offset, int fetchSize);

    int listSize(CommitUrlSearch commitUrlSearch);

    CommitUrlSearch findByUrlAndWebType(String url, WebType webType);

}
