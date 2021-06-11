package com.hbpu.sercurity;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * <p>title: com.qfedu.security</p>
 * author zhuximing
 * description:
 */
  @EnableWebSecurity // 开启security
// 开启@PreAuthorize()注解
// @EnableGlobalMethodSecurity(prePostEnabled=true)
public class MySecurityConfig  extends WebSecurityConfigurerAdapter {
 // 系统资源的访问规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 所有资源必须登录
        http.authorizeRequests().antMatchers("/user/login").permitAll()
                .anyRequest().authenticated() // 其余资源需要访问
                .and().sessionManagement().maximumSessions(1);// 同一账号同时登录最大用户数
        // http.formLogin().loginProcessingUrl("/user/login");

        // 让MyBasicAuthenticationFilter生效
        http.addFilter(new MyBasicAuthenticationFilter(authenticationManager()));
        http.csrf().disable();// 禁用csrf过滤器
        http.cors();
    }
    @Bean
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Authorization");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    /**
     * 描述: 静态资源放行，这里的放行，是不走 Spring Security 过滤器链
     **/
    @Override
    public void configure(WebSecurity web) {
        // 可以直接访问的静态数据
        web.ignoring()
                .antMatchers("/user/login");
    }

}