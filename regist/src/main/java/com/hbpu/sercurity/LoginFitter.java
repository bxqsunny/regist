package com.hbpu.sercurity;

import com.hbpu.entity.User;
import io.lettuce.core.GeoArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Import(LoginFitter.class)
public class LoginFitter implements Ordered, Filter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String url = request.getRequestURI();
        if (url.indexOf("login") > 0){
            doFilter(request, servletResponse,filterChain);
        }
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (stringRedisTemplate.hasKey("user"+principal.getUserId())){
            stringRedisTemplate.expire("user"+principal.getUserId(),30, TimeUnit.MINUTES);
            doFilter(servletRequest,servletResponse,filterChain);
        }
    }

    @Override
    public void destroy() {

    }
}
