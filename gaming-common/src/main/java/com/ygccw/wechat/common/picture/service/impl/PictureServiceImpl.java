package com.ygccw.wechat.common.picture.service.impl;


import com.ygccw.wechat.common.picture.dao.PictureDao;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Inject
    private PictureDao pictureDao;

    @Override
    public void save(Picture picture) {
        picture.setCreateTime(new Date());
        picture.setUpdateTime(new Date());
        picture.setStatus(1);
        picture.setVerify(1);
        pictureDao.save(picture);
    }

    @Override
    public void update(Picture picture) {
        picture.setUpdateTime(new Date());
        pictureDao.update(picture);
    }

    @Override
    public void delete(Long id) {
        pictureDao.delete(id);
    }

    @Override
    public Picture findById(Long id) {
        return pictureDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        pictureDao.deleteStatus(id);
    }

    @Override
    public List<Picture> list(Picture picture, int offset, int fetchSize) {
        return pictureDao.list(picture, offset, fetchSize);
    }

    @Override
    public int listSize(Picture picture) {
        return pictureDao.listSize(picture);
    }

}