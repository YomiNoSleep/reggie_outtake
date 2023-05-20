package com.atyomi.boot.configures.interceptors;

import com.alibaba.fastjson.JSON;
import com.atyomi.boot.common.BaseContext;
import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.domain.User;
import com.google.gson.annotations.JsonAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        User user = (User) session.getAttribute("user");

        if(employee != null){
            BaseContext.threadLocal.set(employee.getId());
            return true;
        }
        if(user != null){
            BaseContext.threadLocal.set(user.getId());
            return true;
        }

        response.getWriter().write(JSON.toJSONString(R.error("用户未登录")));
        return false;
    }
}
