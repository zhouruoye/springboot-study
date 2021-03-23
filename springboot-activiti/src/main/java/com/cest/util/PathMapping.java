package com.cest.util;


import com.cest.controller.BaseController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//映射bpmn文件
@Configuration
public class PathMapping extends BaseController implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //String[] resourceLocation = new String[]{"file:D:\\WangJianIDEA_Test\\activiti-imooc\\src\\main\\resources\\resources\\bpmn\\","classpath:/resources/"};
        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");//默认也有这个路径映射
        registry.addResourceHandler("/bpmn/**").addResourceLocations(BPMN_PathMapping);
    }
}
