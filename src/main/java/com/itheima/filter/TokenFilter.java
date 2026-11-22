package com.itheima.filter;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
@WebFilter("/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURL = request.getRequestURI();

        if("/login".equals(requestURL)){
            log.info("登录请求，放行");
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("token");

        if(token == null || token.isEmpty()){
            log.info("令牌为空，响应401");
            response.setStatus(401);
            return;
        }

        try {
            Claims claims = JwtUtils.parseToken(token);
            Integer empId = claims.get("id", Integer.class);
            if (empId == null) {
                response.setStatus(401);
                return;
            }
            CurrentHolder.setCurrentId(empId);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
            response.setStatus(401);
            return;
        }

        log.info("令牌合法，放行");
        try {
            filterChain.doFilter(request, response);
        } finally {
            CurrentHolder.remove();
        }
    }
}
