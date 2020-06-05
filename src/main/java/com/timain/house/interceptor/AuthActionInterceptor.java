package com.timain.house.interceptor;

import com.timain.house.constants.CommonConstants;
import com.timain.house.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 10:50
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = UserContext.getUser();
        if (null==user) {
            String msg = URLEncoder.encode("请先登录","utf-8");
            String target = URLEncoder.encode(request.getRequestURL().toString(),"utf8");
            if ("GET".equals(request.getMethod())) {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target=" + target);
            } else {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
