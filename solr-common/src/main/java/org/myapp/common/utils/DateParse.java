package org.myapp.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenfeifan on 15/10/28.
 */
public class DateParse {
    private static Logger logger = LoggerFactory.getLogger(DateParse.class);
    
    public static void main(String[] args) {
        System.out.println(toStartEnd("0"));
    }

    public static HashMap<String, Date> toStartEnd(String str) {
        HashMap<String, Date> resultDate = new HashMap<String, Date>();
        HashMap<String, String> result = new HashMap<String, String>();
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.indexOf("-") != -1) {
            String[] strArr = str.split("To");
            result.put("start", strArr[0]);
            result.put("end", strArr[1]);
        } else {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            result.put("end",
                    now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE));
            now.set(Calendar.DATE, now.get(Calendar.DATE) - Integer.parseInt(str) + 1);
            result.put("start",
                    now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE));
        }

        // 转换时间
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = formater.parse(result.get("start"));
        } catch (ParseException e) {
            logger.error("转换时间格式失败：" + result.get("start") + e.getMessage());
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = formater.parse(result.get("end"));
        } catch (ParseException e) {
            logger.error("转换时间格式失败：" + result.get("end") + e.getMessage());
            e.printStackTrace();
        }
        resultDate.put("startDate", startDate);
        resultDate.put("endDate", endDate);
        return resultDate;
    }
}
