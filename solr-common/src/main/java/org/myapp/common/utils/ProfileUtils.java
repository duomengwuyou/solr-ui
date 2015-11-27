package org.myapp.common.utils;

import org.springframework.core.env.Environment;

public class ProfileUtils {
	
	private static String profile = null;
	
	public static final String DEV = "dev";
	
	public static boolean isDev() {
		if(DEV.equals(getCurrentProfile())) {
			return true;
		}
		return false;
	}

	public static String getCurrentProfile() {
		
		if(profile == null)  {
			synchronized (ProfileUtils.class) {
				if(profile == null) {
					profile = SpringUtils.getBean(Environment.class).getActiveProfiles()[0];
				}
			}
		}
		
		return profile;
	}
}
