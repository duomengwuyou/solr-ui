package org.myapp.common.utils;


/**
 * 前台多次使用改变量
 * @author wujunbo
 *
 */
public class CDNUtils {

	public static String cdn;
	public static String staticRoot;
	public static String official;

	public static String getStaticRoot() {

		if(staticRoot == null)  {
			synchronized (CDNUtils.class) {
				if(staticRoot == null) {
					staticRoot = ConfigUtils.getConfig("front.staticRoot");
				}
			}
		}

		return staticRoot;
	}
	public static String getCDN() {

		if(cdn == null)  {
			synchronized (CDNUtils.class) {
				if(cdn == null) {
					cdn = ConfigUtils.getConfig("front.cdn");
				}
			}
		}

		return cdn;
	}
	public static String getOfficial() {

		if(official == null)  {
			synchronized (CDNUtils.class) {
				if(official == null) {
					official = ConfigUtils.getConfig("front.official");
				}
			}
		}

		return official;
	}
}
