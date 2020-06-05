package com.cest.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelDateUtil {

    //Mysql支持的时间戳限制
    static long minTime = Timestamp.valueOf("1970-01-01 09:00:00").getTime();
    static long maxTime = Timestamp.valueOf("2038-01-19 11:00:00").getTime();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //判断 并转换时间格式 ditNumber = 43607.4166666667
    public static String getTime(String ditNumber) {
        //如果不是数字
        if(!isNumeric(ditNumber)){
            return null;
        }
        //如果是数字 小于0则 返回
        BigDecimal bd = new BigDecimal(ditNumber);
        int days = bd.intValue();//天数
        int mills = (int) Math.round(bd.subtract(new BigDecimal(days)).doubleValue() * 24 * 3600);

        //获取时间
        Calendar c = Calendar.getInstance();
        c.set(1900, 0, 1);
        c.add(Calendar.DATE, days - 2);
        int hour = mills / 3600;
        int minute = (mills - hour * 3600) / 60;
        int second = mills - hour * 3600 - minute * 60;
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);

        Date d = c.getTime();//Date
        Timestamp t = Timestamp.valueOf(dateFormat.format(c.getTime()));//Timestamp
        try {
            //时间戳区间判断
            if(minTime <= d.getTime() && d.getTime() <= maxTime){
                return dateFormat.format(c.getTime());
            }else{
                return "outOfRange";
            }
        } catch (Exception e) {
            System.out.println("传入日期错误" + c.getTime());
        }
        return "Error";
    }

    //校验是否数据含小数点
    private static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]+\\.*[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(getTime("43831"));
//        System.out.println(getTime("43607.4166666667"));
    }
}
