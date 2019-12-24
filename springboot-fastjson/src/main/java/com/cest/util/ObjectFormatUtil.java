package com.cest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Created by cestlavie on 2019/11/17.
 */
@Slf4j
public class ObjectFormatUtil {

    public static void objectToJson(Object o,String className){
        try {
            if(Optional.ofNullable(o).isPresent()){
                StringBuffer buf = new StringBuffer();
                buf.append("对象:" + className + "\r\n");
                String s = JSON.toJSONString(o, SerializerFeature.PrettyFormat);
                buf.append(s);
                log.info(buf.toString());
            }else {
                log.info("对象{}为空",className);
            }
        } catch (Exception e){
            log.info("转json异常:{}",e.getMessage());
        }
    }
}
