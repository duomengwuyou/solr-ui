package org.myapp.web.interceptor;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myapp.common.utils.CookieUtils;
import org.myapp.common.utils.MessageHolder;
import org.myapp.common.utils.StringUtils;
import org.myapp.common.web.ApplicationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class LanguageInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageInterceptor.class);
	
	@Autowired
	private CookieLocaleResolver resolver;


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		changeLanguage(request, response);
		return true;
	}
	
	
	private static final String LANGUAGE = "Language";
	private void changeLanguage(HttpServletRequest request,
			HttpServletResponse response) {
		// 如果当前 Cookie 中没有多语言信息，如果没有根据请求Locale设置多语言信息
		String language = CookieUtils.getCookieValue(request, LANGUAGE);
		
        resolver.setCookieDomain(ApplicationFilter.getCrossDomain(request));
		 
		if(StringUtils.isBlank(language)) {
			//resoulver 会设置 cookie LINK spring-config-mvc.xml
            Locale locale = request.getLocale();
            if(request.getLocale().equals(Locale.CHINESE) || request.getLocale().equals(Locale.SIMPLIFIED_CHINESE)) {
                locale = Locale.SIMPLIFIED_CHINESE;
            } else {
            	locale = Locale.ENGLISH;
            }
            
			resolver.setLocale(request, response, locale);
			MessageHolder.setLocale(locale);
		} else {
			Locale locale = request.getLocale();
			if(language.indexOf("zh") != -1) {
				locale = Locale.SIMPLIFIED_CHINESE;
			} else {
				locale = Locale.ENGLISH;
			}
			
			//如果当前cooike为非系统设置的语言，重新设置cookie 
			if(!language.equals("zh_CN") && !language.equals("en")) {
				resolver.setLocale(request, response, locale);
			}
			
			MessageHolder.setLocale(locale);
		}
		

		if(request.getParameter("language") != null) {
			language = request.getParameter("language");
			if(StringUtils.equals(language, "zh_CN") || StringUtils.equals(language, "zh")) {
				MessageHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
				//resoulver 会设置 cookie LINK spring-config.xml
				resolver.setLocale(request, response, Locale.SIMPLIFIED_CHINESE);
				LOGGER.info(request.getRemoteAddr() + " languange change " + request.getLocale());
			} else {
				MessageHolder.setLocale(Locale.ENGLISH);
				//resoulver 会设置 cookie LINK spring-config.xml
				resolver.setLocale(request, response, Locale.ENGLISH);
				LOGGER.info(request.getRemoteAddr() + " languange change " + request.getLocale());
			}
		}
	}


}