package org.myapp.web.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证session
        if (!validateSession(request, response)) {
            return false;
        }
        return true;
    }

    private boolean validateSession(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 过滤包含Login 和 register 的页面
        String url = request.getRequestURI();
        logger.info("拦截器：" + url);

        Boolean isLogin = (Boolean) request.getSession().getAttribute("isLogin");
        isLogin = true;
        if (isLogin != null && isLogin == true) {
            return true;
        } else {
            try {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } catch (ServletException e) {
                logger.error("跳转网页失败！ index.jsp");
            }
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
