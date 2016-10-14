package com.ygccw.website.pc.picture.service;

import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.service.TagsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class PictureWebService {
    @Inject
    PictureService pictureService;
    @Inject
    TagsService tagsService;

    public List<Picture> pictureList(int offset, int fetchSize) {
        Picture picture = new Picture();
        return pictureService.list(picture, offset, fetchSize);
    }

}
