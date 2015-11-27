package org.myapp.common.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 获取配置信息的工具类
 */
public class ConfigUtils {

	private static Properties props = null;
	
	static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
	/** 根据KEY获取值 **/
	public static String getConfig(String key) {
		if(props == null) {
			synchronized (ConfigUtils.class) {
				if(props == null) {
					try {
						props = new Properties();
						props.load(ConfigUtils.class.getResourceAsStream("/resources."
								+ ProfileUtils.getCurrentProfile()
								+ ".properties"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}


		return props.getProperty(key);
	}
}