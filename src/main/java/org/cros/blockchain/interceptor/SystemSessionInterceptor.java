/**
 * Project Name:risk-control
 * File Name:SystemSessionInterceptor.java
 * Package Name:com.usolv.risk.interceptor
 * Date:2016年8月8日下午2:25:40
 * Copyright (c) 2016, hokuny@foxmail.com All Rights Reserved.
 *
 */

package org.cros.blockchain.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:SystemSessionInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年8月8日 下午2:25:40 <br/>
 *
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
public class SystemSessionInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        // session中获取用户名信息
        Object obj = session.getAttribute("loginUser");
        if (obj == null || "".equals(obj.toString())) {
            response.sendRedirect(request.getSession().getServletContext().getContextPath());
            return false;
        }
        return true;
    }

}
