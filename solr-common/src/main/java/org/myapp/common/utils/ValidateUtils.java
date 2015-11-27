package org.myapp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ValidateUtils {
	
	
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}
	
	
	public static boolean checkPassword(String password) {

		boolean upper = false;
		boolean lower = false;
		boolean number = false;

		if (StringUtils.isBlank(password)) {
			return false;
		}

		for (int i = 0; i < password.length(); i++) {
			char node = password.charAt(i);
			if (((node >= 'a') && (node <= 'z'))
					|| ((node >= 'A') && (node <= 'Z'))
					|| ((node >= '0') && (node <= '9'))) {

				if ((node >= 'a') && (node <= 'z')) {
					lower = true;
				}

				if ((node >= 'A') && (node <= 'Z')) {
					upper = true;
				}

				if ((node >= '0') && (node <= '9')) {
					number = true;
				}

			} else {
				return false;
			}

		}

		if (upper && lower && number) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 电子邮箱
	 */

	public static final String EMAIL = "^[a-zA-Z0-9_\\-\\.]{1,}@([a-zA-Z0-9_\\-]{1,}\\.[a-zA-Z0-9]{1,}){1,}$";

	/**
	 * 判断字符串<code>content</code>是否包含正则表达式<code>regex</code>
	 * 如果是则返回<ocde>true</code>
	 * 
	 * @param content
	 *            字符串
	 * @param regex
	 *            正则表达式
	 * @return 是否包含
	 */
	public static boolean contain(String content, String regex) {
		if (content == null || regex == null) {
			return false;

		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		return matcher.find();
	}
	
	
	public static String NUMBER = "^(\\d+)$|^(0|\\d+)\\.(\\d+)$";
	public static String VALIDEN = "^[\\w]+$";
	public static String VALIDSTR = "^[\\w\\u4e00-\\u9fa5]+$";
	public static String VALIDPKG = "^((\\w|\\d)+.)+(\\w|\\d)+$";
	public static String VALIDZH = "^[\\u4e00-\\u9fa5]+$";
//	public static String VALIDTEXT = "^[^<>]+$";
	public static String VALIDVERSION = "^(\\d+.)+\\d+$";
	public static String VALID_MB= "^\\d+([Mm][Bb]?)$|^(0|\\d+)\\.(\\d+)([Mm][Bb]?)$";
	public static String VALID_KB= "^\\d+([Kk][Bb]?)$|^(0|\\d+)\\.(\\d+)([Kk][Bb]?)$";

	public static String POSITIVE_INTEGER = "^[1-9]+\\d*$"; //必须是1-9开头的

	public static String URL = "^http(s)?:\\/\\/";
	
	
	
	/**
	 * 校验文本类
	 * 
	 * @return
	 */
	public static boolean validText(String str, int min, int max) {
		if (StringUtils.isBlank(str)) {
			return true;
		}else{
			if (min > max) {
				return false;
			}
			if (str.length() < min || str.length() > max) {
				return false;
			}
			return true;
		}
	}

	
	/**
	 * 校验字符串 "中文" "英语" "_"
	 * 
	 * @return
	 */
	public static boolean validStr(String str, int min, int max) {
		if (StringUtils.isBlank(str)) {
			return true;
		}else{
			if (min > max) {
				return false;
			}
//			if (!matchPattern(str, VALIDSTR)) {
//				return false;
//			}
			if (str.length() < min || str.length() > max) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 校验包含中文的字符串
	 * 
	 * @return
	 */
	public static boolean validZHStr(String str, int min, int max) {

		if (StringUtils.isBlank(str) && min == 0) {
			return true;
		}

		if (StringUtils.isBlank(str)) {
			return false;
		}

		if (min > max) {
			return false;
		}
		if (!matchPattern(str, VALIDZH)) {
			return false;
		}
		if (str.length() < min || str.length() > max) {
			return false;
		}
		return true;
	}

	/**
	 * 校验包含英文的字符串
	 * 
	 * @return
	 */
	public static boolean validENStr(String str, int min, int max) {

		if (StringUtils.isBlank(str) && min == 0) {
			return true;
		}

		if (StringUtils.isBlank(str)) {
			return false;
		}

		if (min > max) {
			return false;
		}
		if (!matchPattern(str, VALIDEN)) {
			return false;
		}
		if (str.length() < min || str.length() > max) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验包名
	 */
	public static boolean validPkg(String str, int min, int max){

		if (StringUtils.isEmpty(str) || min > max) {
			return false;
		}

		if (str.length() < min || str.length() > max) {
			return false;
		}
		
		if (!matchPattern(str, VALIDPKG)) {
			return false;
		}
		
		return true;
	}
	/**
	 * 校验类
	 * 
	 * @return
	 */
	public static boolean valid(String str, String reg, int min, int max) {
		if (min > max) {
			return false;
		}

		if (StringUtils.isBlank(str) && min == 0) {
			return true;
		}
		if (StringUtils.isBlank(str)) {
			return false;
		}
		if (!matchPattern(str, reg)) {
			return false;
		}
		if (str.length() < min || str.length() > max) {
			return false;
		}
		return true;
	}
	
	public static boolean validNotNullStr(String str, int min, int max) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		return validStr(str, min, max);
	}
	
	public static boolean validNotNullText(String str, int min, int max) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		return validText(str, min, max);
	}

	/**
	 * 校验字符长度
	 */
	public static boolean checkLength(String str, int min, int max) {
		
		if(str == null) return false;
		
		if (str.length() < min || str.length() > max) {
			return false;
		}
		return true;
	}
	public static boolean isNumber(String num){
		return matchPattern(num,NUMBER);
	}
	
	public static boolean isPositiveInteger(String num){
		return matchPattern(num,POSITIVE_INTEGER);
	}
	

	public static boolean validURL(String url){
		return matchPattern(url,URL) ;
	}
	
	public static boolean matchPattern(String value, String reg) {
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(value);
		return mat.find();
	}
	

}
