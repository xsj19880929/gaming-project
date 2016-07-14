package com.ygccw.wechat.common.picture.service;


import com.ygccw.wechat.common.picture.entity.Picture;

import java.util.List;

/**
 * @author soldier
 */
public interface PictureService {
    void save(Picture picture);

    void update(Picture picture);

    void delete(Long id);

    Picture findById(Long id);

    void deleteStatus(Long id);

    List<Picture> list(Picture picture, int offset, int fetchSize);

    int listSize(Picture picture);

}
