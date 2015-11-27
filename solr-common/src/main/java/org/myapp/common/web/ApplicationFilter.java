package org.myapp.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.myapp.common.redis.RedisSessionInfoUtils;
import org.myapp.common.utils.CookieUtils;
import org.myapp.common.utils.ProfileUtils;
import org.myapp.common.utils.StringUtils;
import org.myapp.common.utils.TokenProcessor;

public class ApplicationFilter implements Filter {
	
	public static final String USERKEY = "user-key";
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationFilter.class);
	
	
	public String setCookieUserKey(HttpServletRequest request, HttpServletResponse response) {
		
	
		String userKey = CookieUtils.getCookieValue(request, USERKEY);

		System.out.println(userKey + ":userKey " + request.getRequestURL());
		//Cookie 中没有设置USERKEY
		if(StringUtils.isBlank(userKey)) {
			userKey = TokenProcessor.getInstance().generateToken();

			if(ProfileUtils.isDev()) {
				 CookieUtils.setCookie(response, USERKEY, userKey, 1 * 24 * 60 * 60);
			} else {
				response.setHeader("Set-Cookie",USERKEY + "=" + userKey  + ";Path=/;Domain=" + getCrossDomain(request) + ";Max-Age=" + (1 * 24 * 60 * 60) + ";httponly");
			}
		}
		
		RedisSessionInfoUtils.setUserKey(userKey);
		return userKey;
	}


    public static  String  getCrossDomain(HttpServletRequest request) {
        String serverName = request.getServerName();
        
        if("localhost".equals(serverName)) {
        	return "";
        }
        
        
        
        String[] domainArray = serverName.split("\\.");

        // check that the domain isn't an IP address
        if (domainArray.length == 4) {
            boolean isIpAddress = true;
            for (String domainSection : domainArray) {
                if (!isIntegerInRange(domainSection, 0, 255)) {
                    isIpAddress = false;
                    break;
                }
            }
            if (isIpAddress) return serverName;
        }


        if (domainArray.length > 2) {
           return  "." + domainArray[domainArray.length - 2] + "." + domainArray[domainArray.length - 1];
        }


        return serverName;
    }


    public static boolean isIntegerInRange(String s, int a, int b) {
        if(StringUtils.isBlank(s)) return false;

        int source = 0;
        try {
           source = Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }

        if(source >= a && source <= b) {
            return true;
        }

        return false;
    }



	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		try {
			
			setCookieUserKey(httpRequest, httpResponse);
			
			filterChain.doFilter(new XssHttpServletRequestWrapper(
	                (HttpServletRequest)request), response);
			
			//清楚 threadlocal变量，避免线程重用导致问题
			RedisSessionInfoUtils.clear();
		} catch (Exception e) {
			e.printStackTrace();
			//304 页面
			httpResponse.sendError(500, e.getMessage());
		}

	}
	
	

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	
	
}