package org.myapp.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConstantVariable {
    // 应用名称
    public static final String BOOSTER_NAME = "booster";
    public static final String SAVER_NAME = "saver";
    public static final String CLEAN_MASTER_NAME = "cleanmaster";
    public static final String QIHOO_SECURITY_NAME = "qihoosecurity";
    public static final String CM_SPEED_BOOSTER = "cmbooster";
    public static final String BATTERY_DOCTOR = "batterydoctor";
    public static final String DU_LAUNCHER = "dulauncher";
    public static final String APUS_LAUNCHER = "apuslauncher";

    // 应用名称
    public static final HashMap<String, String> APP_NAME_MAP = new HashMap<String, String>();
    static {
        APP_NAME_MAP.put(BOOSTER_NAME, "DU Speed Booster");
        APP_NAME_MAP.put(SAVER_NAME, "DU Battery Saver");
        APP_NAME_MAP.put(CLEAN_MASTER_NAME, "Clean Master");
        APP_NAME_MAP.put(QIHOO_SECURITY_NAME, "Qihoo Security");
        APP_NAME_MAP.put(CM_SPEED_BOOSTER, "CM Speed Booster");
        APP_NAME_MAP.put(BATTERY_DOCTOR, "Battery Doctor");
        APP_NAME_MAP.put(DU_LAUNCHER, "DU Launcher");
        APP_NAME_MAP.put(APUS_LAUNCHER, "APUS Launcher");
    }

    // 竞品应用匹配
    public static final HashMap<String, List<String>> COMPETITIVE_PRODUCTS = new HashMap<String, List<String>>();
    static {
        List<String> boosterApps = new ArrayList<String>();
        boosterApps.add(BOOSTER_NAME);
        boosterApps.add(CLEAN_MASTER_NAME);
        boosterApps.add(QIHOO_SECURITY_NAME);
        boosterApps.add(CM_SPEED_BOOSTER);
        COMPETITIVE_PRODUCTS.put(BOOSTER_NAME, boosterApps);

        List<String> saverApps = new ArrayList<String>();
        saverApps.add(SAVER_NAME);
        saverApps.add(BATTERY_DOCTOR);
        COMPETITIVE_PRODUCTS.put(SAVER_NAME, saverApps);

        List<String> dulauncherApps = new ArrayList<String>();
        dulauncherApps.add(DU_LAUNCHER);
        dulauncherApps.add(APUS_LAUNCHER);
        COMPETITIVE_PRODUCTS.put(DU_LAUNCHER, dulauncherApps);
    }

    // Controller json返回结果key
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String TOTAL_SIZE = "totalSize";
    public static final String TOTAL_PAGES = "pages";

    // 默认数值
    public static final int DEFAULT_PAGESIZE = 10;

    // 邮箱后缀
    public static final String EMAIL_SUFFIX = "@baidu.com";

    // 注册token
    public static final String REGISTER_TOKEN = "mobula";

    // 默认图表y轴数据
    public static final Float DEFAULT_Y_VALUE = 0f;

    // app version默认值
    public static final String APP_VERSION_CODE_NULL = "-1";

    // 图表的title
    public static final String A_S_R_X_TITLE = "Date";
    public static final String A_S_R_Y_TITLE = "Average Score";

    public static final String U_T_I_X_TITLE = "Date";
    public static final String U_T_I_Y_TITLE = "Total Increasing Num";

    public static final String U_D_I_X_TITLE = "Date";
    public static final String U_D_I_Y_TITLE = "User Detail Increasing Num";
}
