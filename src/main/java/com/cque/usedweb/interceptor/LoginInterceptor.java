package com.cque.usedweb.interceptor;

import com.cque.usedweb.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Chenge on 2020.2.12 13:33
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final String PERMIT_URL = "index";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession s = request.getSession();
        Person person = (Person) s.getAttribute("user");
        if (person != null){
            return true;
        }
        // 不符合条件的跳转主页()
        //不能重定向打controller,因为
        //拦截器是拦截controller的, 拦截了controller后又重定向或者转发到controller, 又是拦截, 死循环重定向或者转发
        response.sendRedirect("/permitLogin.html");
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
