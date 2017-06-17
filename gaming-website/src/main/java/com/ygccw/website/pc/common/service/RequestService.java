package com.ygccw.website.pc.common.service;

import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author soldier
 */
@Service
public class RequestService {
    /**
     * 返回404请求
     *
     * @param request
     * @param response
     */


    public void redirectNoFound(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        try {
            request.getRequestDispatcher("/404.html").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
