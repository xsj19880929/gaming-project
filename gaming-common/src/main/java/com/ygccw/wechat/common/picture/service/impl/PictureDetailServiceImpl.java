package com.ygccw.wechat.common.picture.service.impl;

import com.ygccw.wechat.common.picture.dao.PictureDetailDao;
import com.ygccw.wechat.common.picture.entity.PictureDetail;
import com.ygccw.wechat.common.picture.service.PictureDetailService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class PictureDetailServiceImpl implements PictureDetailService {
    @Inject
    private PictureDetailDao pictureDetailDao;

    @Override
    public void save(PictureDetail pictureDetail) {
        pictureDetail.setCreateTime(new Date());
        pictureDetail.setUpdateTime(new Date());
        pictureDetail.setStatus(1);
        pictureDetailDao.save(pictureDetail);
    }

    @Override
    public void update(PictureDetail pictureDetail) {
        pictureDetail.setUpdateTime(new Date());
        pictureDetailDao.update(pictureDetail);
    }

    @Override
    public PictureDetail findById(Long id) {
        return pictureDetailDao.findById(id);
    }


    @Override
    public List<PictureDetail> listByPictureId(Long pictureId) {
        return pictureDetailDao.listByPictureId(pictureId);
    }

    @Override
    public void deleteByPictureId(Long pictureId) {
        pictureDetailDao.deleteByPictureId(pictureId);
    }

    @Override
    public void saveList(List<PictureDetail> pictureDetailList) {
        for (PictureDetail pictureDetail : pictureDetailList) {
            pictureDetail.setCreateTime(new Date());
            pictureDetail.setUpdateTime(new Date());
            pictureDetail.setStatus(1);
            pictureDetailDao.save(pictureDetail);
        }

    }
}
