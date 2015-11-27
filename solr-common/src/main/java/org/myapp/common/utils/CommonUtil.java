package org.myapp.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一些常用的方法
 * 
 * @author xinglong
 *
 */
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 判断是否为数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 获取前一天数据
     * 
     * @param date
     * @return
     */
    public static Date getNextDay(java.util.Date date, int plusDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, plusDay);
        Date result = new Date(calendar.getTime().getTime());
        return result;
    }

    /**
     * 转换数据格式 如：9,645,369 -> 9645369
     * 
     * @param sourceData
     * @return
     */
    public static String parseLongData(String sourceData) {
        String[] tokens = sourceData.split(",");
        String result = "";
        for (String s : tokens) {
            result += s;
        }
        return result;
    }

    /**
     * 返回当天的开始毫秒数
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getDayStartMills(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回当天的结束毫秒数
     * 
     * @param date
     * @return
     */
    public static long getDayEndMills(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回指定范围内的随机数
     * 
     * @param min
     * @param max
     * @return
     */
    public static int getRandomValue(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    // 获取当前月的开始时间
    public static long getMonthStartTime() {
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        calendar.setTime(date);

        int maxDate = calendar.getActualMaximum(Calendar.DATE);
        int curDate = date.getDate();
        calendar.add(Calendar.DAY_OF_MONTH, 1 - curDate);
        return calendar.getTimeInMillis();
    }

    // 获取当前月的结束时间
    public static long getMonthEndTime() {
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        calendar.setTime(date);

        int maxDate = calendar.getActualMaximum(Calendar.DATE);
        int curDate = date.getDate();
        calendar.add(Calendar.DAY_OF_MONTH, maxDate - curDate + 1);
        return calendar.getTimeInMillis();
    }

    // 将时间从1444804420128转换为2015-10-14T06:33:40Z
    public static String changeDateStr(long oldTime) {
        Date newDate = new Date(oldTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = formatter.format(newDate);
        return result;
    }

    // 将时间从Sat Oct 24 00:00:00 CST 2015 转为 2015-10-24
    public static String changeDateStr(Date oldDateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String result = formatter.format(oldDateStr);
        return result;
    }

    // 将回复率转化为保留两位小数的百分数
    public static String formatFloat(float oldValue) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String p = decimalFormat.format(oldValue);
        Float result = Float.parseFloat(p);
        return result + "%";
    }

    // 格式化float数据小数点后三位
    public static Float formatThreeFloat(float oldValue) {
        DecimalFormat decimalFormat = new DecimalFormat(".000");
        String newValue = decimalFormat.format(oldValue);
        return Float.parseFloat(newValue);
    }

    // 获取客户端地址
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.length() > 12) {
            String[] tokens = ip.split(",");
            ip = tokens[0];
        }
        return ip;
    }

    public static String convertUnicodeStr(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomValue(0, 2));
    }
}
