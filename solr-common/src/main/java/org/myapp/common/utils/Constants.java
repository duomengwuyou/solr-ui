package org.myapp.common.utils;



public class Constants {
	
	
	public static final String cachePrefix = "mytest:";
	 
	public static final  String USER_ACCOUNT_STATUS_ALLOW = "1201";
	public static final String USER_ACCOUNT_STATUS_NOT_ALLOW = "1200";
	
	public static final String REGISTER_VALIDATE = "1301";
	public static final String REGISTER_NO_VALIDATE = "1300";
	
	
	
	public static final String APP_INIT = "1001";
	public static final String APP_COMMIT = "1002";
	public static final String APP_PASS = "1003";
	public static final String APP_UN_UPLOAD = "1004";
	public static final String APP_BACK = "1009";
	
	public static final String PLACEMENT_ENAVLE = "1101";
	public static final String PLACEMENT_DISABLE = "1100";

    //审核中
    public static final String PAY_COMMIT = "1301";
    //已打款
    public static final String PAY_PASS= "1302";


    public static final String PAY_ENABLED = "1303";
    public static final String PAY_DISABLE = "1304";

    //待付款
    public static final String PAY_WAIT_PASS = "1305";
    //驳回
    public static final String PAY_NO_PASS = "1306";


	
	/**
	 * 删除状态
	 */
	public static final  int ENABLED = 1;
	public static final  int DELETE = 0;
	
	
	public static final int PLACEMENT_COUNT = 10;
	public static final int PAGE_SIZE = 10;
	
	public static final int TOKEN_TIMEOUT = 2;
	
	
	/**
	 * APP 审核状态
	 */
	public static final String APP_AUDIT_STATUS_SYSTEM_PASS = "1703"; //系统审核通过
	public static final String APP_AUDIT_STATUS_SYSTEM_BACK = "1705"; //系统审核不通过
	public static final String APP_AUDIT_STATUS_SYSTEM_PAUSE = "1706"; //系统暂停
	public static final String APP_AUDIT_STATUS_BACK = "1707";  //人工退回
	public static final String APP_AUDIT_STATUS_PASS = "1708";  //人工审核通过
	public static final String APP_AUDIT_STATUS_COMMIT = "1709";  //人工审核通过
	
	public static final String APP_AUDIT_TYPE_MANUAL = "1701";
	public static final String APP_AUDIT_TYPE_SYSTEM = "1702";
}
