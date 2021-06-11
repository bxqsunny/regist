package com.hbpu.sercurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbpu.entity.ResultDto;
import com.hbpu.entity.User;
import com.hbpu.util.JwtUtils;
import com.hbpu.util.RsaUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ResourceUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.util.List;

/**
 * <p>title: com.wfx.filter</p>
 * author
 * description:
 */

public class MyBasicAuthenticationFilter extends BasicAuthenticationFilter {

    public MyBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //重写过滤器核心方法

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
       // 1:获取jwt令牌
        String token = request.getHeader("token");
        if(!request.getMethod().equals("OPTIONS")){
            if (token == null) {
                response(response,new ResultDto(false,"请指定token"));
                return;
            }

            try {
                // 2:解析jwt令牌
                String publicKeyPath = ResourceUtils.getFile("classpath:ras_public").getPath();
                PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);

                // 从令牌中解析数据
                User userInfo = (User) JwtUtils.getInfoFromToken(token, publicKey, User.class);

                // 放入springsecurity的上下文
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, null);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // 放行
                chain.doFilter(request, response) ;

            }catch (Exception e){
                // 异常捕获太大
                e.printStackTrace();
                //  令牌解析失败
                response(response,new ResultDto(false,"请输入正确的令牌"));
            }
        }else {
            // 放行
            chain.doFilter(request, response) ;
        }
    }


    private void  response(HttpServletResponse response, ResultDto res){
       try {
           response.setContentType("application/json;charset=utf-8");
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           PrintWriter out = response.getWriter();
           out.write(new ObjectMapper().writeValueAsString(res));
           out.flush();
           out.close();
       }catch (Exception e){
            e.printStackTrace();
       }

    }
}