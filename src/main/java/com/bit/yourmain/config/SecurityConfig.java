package com.bit.yourmain.config;

import com.bit.yourmain.config.auth.CustomOAuth2UserService;
import com.bit.yourmain.domain.Role;
import com.bit.yourmain.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersService usersService;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/mypage").authenticated()         // 로그인시 접속가능
                .antMatchers("/semi").hasRole(Role.SEMI.name()) // semi 권한 접속가능
                .antMatchers("/user").hasRole(Role.USER.name()) // user 권한 접속가능
                .anyRequest().permitAll()                                  // 모두 접속가능
                .and()
                .formLogin()
                .loginPage("/loginpage")
                .usernameParameter("id")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/") // 로그인시 이동페이지
                .permitAll()
                .successHandler(customSuccessHandler)
                .failureHandler(customFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // 로그아웃시 갈 주소
                .invalidateHttpSession(true)    // 세션 초기화
                .deleteCookies("JSESSIONID", "remember-me")
                .and().exceptionHandling().accessDeniedPage("/403")    // 권한오류시 이동페이지
                .and()
                .oauth2Login().userInfoEndpoint()
                .userService(customOAuth2UserService);
        http
                .sessionManagement()    // 세션관리기능
                .maximumSessions(-1) // 동시에 사용가능한 세션 수
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단
                .expiredUrl("/");    // 세션이 만료된경우
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(passwordEncoder());
    }

}