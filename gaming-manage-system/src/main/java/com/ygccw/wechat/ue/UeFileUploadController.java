package com.ygccw.wechat.ue;

import com.baidu.ueditor.define.BaseState;
import com.google.common.io.ByteStreams;
import com.ygccw.wechat.utils.Files;
import core.api.file.api.FileMetaAPIService;
import core.api.file.api.ListImage;
import core.api.file.client.FileUploadClient;
import core.api.file.controller.FileUploadResponse;
import core.framework.web.site.session.RequireSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author nick
 */
@Controller
public class UeFileUploadController {
    private final Logger logger = LoggerFactory.getLogger(UeFileUploadController.class);
    @Inject
    Environment env;
    @Inject
    FileUploadClient fileUploadClient;
    @Inject
    FileMetaAPIService fileMetaAPIService;

    @RequireSession
    @RequestMapping(value = "/ueditor/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public String index(@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request) {
        FileUploadResponse upload = null;
        BaseState state = new BaseState(true);
        try {
            upload = fileUploadClient.upload(upfile, request.getParameter("group"));
            state.putInfo("url", upload.getPath());
            state.putInfo("title", upfile.getName());
            state.putInfo("original", upfile.getOriginalFilename());
        } catch (Exception e) {
            state.setState(false);
            logger.error(e.getMessage(), e);
        }
        return state.toJSONString();
    }

    @RequireSession
    @RequestMapping(value = "/ueditor/file/list", method = RequestMethod.GET)
    @ResponseBody
    public ListImage list(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        ListImage listImage = new ListImage();
        try {
            listImage = fileMetaAPIService.listByGroup(start, Integer.parseInt(request.getParameter("size")), "ue");
            for (ListImage.Image image : listImage.getList()) {
                //image.setUrl("http://file.dev.ygccw.com" + image.getUrl());
                image.setUrl(Files.contact(env.getRequiredProperty("out.image.downloadUrl"), "image", image.getUrl()) + "?raw=true");
            }
            listImage.setState("SUCCESS");
        } catch (Exception e) {
            listImage.setState("ERROR");
            logger.error(e.getMessage(), e);
        }
        return listImage;
    }

    @RequireSession
    @RequestMapping(value = "/ueditor/config", method = RequestMethod.GET)
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("/ue/config.json");
        ByteStreams.copy(in, response.getOutputStream());
    }
}
