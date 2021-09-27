package com.cest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cest.pojo.User;
import com.cest.pojo.Word;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * SerializerFeature测试类
 * Created by cestlavie on 2019/12/24.
 */
@Slf4j
public class SerializerFeatureTest {

    private static Word word;

    public static void main(String[] args) {
        init();
        //testTime();
        //testNull();
        //beanToArray();
        sortByfield();
    }

    /**
     * 1 时间测试
     */
    public static void testTime(){
        word.setMap(null);
        word.setList(null);
        //正常new date() 通过json输出时间戳
        log.info("\n" + JSON.toJSONString(word));
        //{"a":"a","b":2,"c":true,"d":"d","date":1577153342482,"e":""}

        //修改日期格式
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        log.info("\n" + JSON.toJSONString(word, SerializerFeature.WriteDateUseDateFormat));
        //{"a":"a","b":2,"c":true,"d":"d","date":"2019-12-24 10:09:02","e":""}

        //UseISO8601DateFormat:Date使用ISO8601格式输出，默认为false
        log.info("\n" + JSON.toJSONString(word, SerializerFeature.UseISO8601DateFormat));
        //{"a":"a","b":2,"c":true,"d":"d","date":"2019-12-24T10:09:02.482+08:00","e":""}
    }

    /**
     * 2 判断是否为空
     */
    public static void testNull(){
        word.setMap(null);
        word.setList(null);
        word.setA(null);
        //默认
        log.info("默认\n" + JSON.toJSONString(word));
        //设置双引号为单引号
        log.info("设置双引号为单引号\n" + JSON.toJSONString(word,SerializerFeature.UseSingleQuotes));
        //设置map为null为
        log.info("设置map为null为\n" + JSON.toJSONString(word,SerializerFeature.WRITE_MAP_NULL_FEATURES));
        //{map:null,date:1577153910264,b:2,list:[],d:"d",e:"",a:"a",f:"",c:true}
        log.info("设置map为null为\n" + JSON.toJSONString(word,SerializerFeature.WriteMapNullValue));
        //{"a":"a","b":2,"c":true,"d":"d","date":1577153910264,"e":"","f":null,"list":null,"map":null}
        //设置list 如果是 null 则为 []
        log.info("设置list 如果是 null 则为 []\n" + JSON.toJSONString(word,SerializerFeature.WriteNullListAsEmpty));
        //设置string null为""
        log.info("设置string null为\"\"\n" + JSON.toJSONString(word,SerializerFeature.WriteNullStringAsEmpty));
    }

    /**
     * 3 对象转成array
     */
    public static void beanToArray(){
        //默认
        log.info("默认\n" + JSON.toJSONString(word));
        //将对象转为array
        log.info("将对象转为array\n" + JSON.toJSONString(word,SerializerFeature.BeanToArray));
    }


    /**
     * 4 SortField:按字段名称排序后输出
     */
    public static void sortByfield(){
        //默认
        log.info("默认\n" + JSON.toJSONString(word));
        //解决循环引用
        log.info("解决循环引用\n" + JSON.toJSONString(word, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.PrettyFormat));
        //字段名称排序后输出
        log.info("字段名称排序后输出\n" + JSON.toJSONString(word,SerializerFeature.SortField,SerializerFeature.PrettyFormat));
        //字段名称排序后输出
        log.info("字段名称排序后输出\n" + JSON.toJSONString(word,SerializerFeature.MapSortField,SerializerFeature.PrettyFormat));
    }

    /**
     * 格式化bean
     */
    public static void formatBean(){
        //默认
        log.info("默认\n" + JSON.toJSONString(word));
        //格式化后
        log.info("字段名称排序后输出\n" + JSON.toJSONString(word,SerializerFeature.PrettyFormat));
    }

    /**
     * 初始化word
     */
    private static void init() {
        word = new Word();
        word.setA("a");
        word.setB(2);
        word.setC(true);
        word.setD("d");
        word.setE("");
        word.setF(null);
        word.setDate(new Date());

        List<User> list = new ArrayList<User>();
        User user1 = new User();
        user1.setId("1");
        user1.setOld("11");
        user1.setName("用户1");
        user1.setAdd("北京");
        User user2 = new User();
        user2.setId("2");
        user2.setOld("22");
        user2.setName("用户2");
        user2.setAdd("上海");
        User user3 = new User();
        user3.setId("3");
        user3.setOld("33");
        user3.setName("用户3");
        user3.setAdd("广州");

        list.add(user3);
        list.add(user2);
        list.add(null);
        list.add(user1);

        word.setList(list);

        Map<String , Object> map = new HashMap();
        map.put("mapa", "mapa");
        map.put("mapo", "mapo");
        map.put("mapz", "mapz");
        map.put("user1", user1);
        map.put("user3", user3);
        map.put("user4", null);
        map.put("list", list);
        word.setMap(map);
    }
}
