package com.yr.utils;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 重写CommonsMultipartResolver中的isMultipart实现PUT请求上传文件
 * @author nysheng
 */
public class MyCommonsMultipartResolver extends CommonsMultipartResolver {
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        return !(POST_METHOD.equalsIgnoreCase(request.getMethod())||PUT_METHOD.equalsIgnoreCase(request.getMethod()))
                ?false
                :FileUploadBase.isMultipartContent(new ServletRequestContext(request));
    }
}
