package com.cest.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 自定义异常数据反显
 * Created by cestlavie on 2019/12/19.
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        if("500".equals(errorAttributes.get("status").toString())){
            errorAttributes.put("message","服务器500异常");
        }

        if("404".equals(errorAttributes.get("status").toString())){
            errorAttributes.put("message","服务器404异常");
        }
        return errorAttributes;
    }
}



