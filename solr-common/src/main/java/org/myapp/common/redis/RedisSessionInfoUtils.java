package org.myapp.common.redis;

import java.util.Map;

import org.myapp.common.utils.BeanMapper;
import org.myapp.common.utils.ConfigUtils;
import org.myapp.common.utils.SpringUtils;
import org.myapp.common.utils.StringUtils;

public class RedisSessionInfoUtils {
	private static ThreadLocal<RedisSessionInfo> redisSessionInfo = new  ThreadLocal<RedisSessionInfo>();
	private static ThreadLocal<String> userKeyToken = new ThreadLocal<String>();
	
	public static void set(Map<String, Object> map) {
        System.out.println(Thread.currentThread().getName());
		redisSessionInfo.set(BeanMapper.map(map, RedisSessionInfo.class));
	}

    public static void clear () {
        redisSessionInfo.remove();
        userKeyToken.remove();
    }

    public static void removeRedisSessionInfo () {
        redisSessionInfo.remove();
    }
	
	public static void set(RedisSessionInfo rsi) {
		redisSessionInfo.set(rsi);
	}
	
	public static void setUserKey(String userKey) {
		userKeyToken.set(userKey);
	}
	
	public static String getUserKey() {
		if(StringUtils.isBlank(userKeyToken.get())) {
			 throw new RuntimeException("必须设置 user-key cookie");
		}
		return userKeyToken.get();
	}
	
	public static RedisSessionInfo get() {
		return redisSessionInfo.get();
	}
	
	
	public static Long getAccountId() {
		return redisSessionInfo.get().getAccountid();
	}
	
	public static String getUsername() {

        System.out.println(Thread.currentThread().getName());
		
		if(redisSessionInfo.get() == null) return null;
		
		return redisSessionInfo.get().getUsername();
	}
	
	public static String getEmail() {
		return redisSessionInfo.get().getEmail();
	}
	

	public static boolean isLogin() {
		if(getUsername() == null) {
			RedisService redisService = SpringUtils.getBean("redisService");
			String userKey = RedisSessionInfoUtils.getUserKey();
			if(!StringUtils.isBlank(userKey) && redisService.exists(userKey)) {
				//设置Session 时间为 30 分钟
				RedisSessionInfoUtils.set(redisService.getMap(userKey));
			}

			if(RedisSessionInfoUtils.getUsername() == null) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
	
	public static String getLogoutUrl() {
		return ConfigUtils.getConfig("cas.logouturl");
	}
	
	public static String getMain() {
		return ConfigUtils.getConfig("front.official");
	}
	
	
}
