package com.ygccw.wechat.common.picture.service;


import com.ygccw.wechat.common.picture.entity.PictureDetail;

import java.util.List;

/**
 * @author soldier
 */
public interface PictureDetailService {
    void save(PictureDetail pictureDetail);

    void update(PictureDetail pictureDetail);

    PictureDetail findById(Long id);

    List<PictureDetail> listByPictureId(Long pictureId);

    void saveList(List<PictureDetail> pictureDetailList);

    void deleteByPictureId(Long pictureId);
}
