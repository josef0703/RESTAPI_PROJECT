package com.example.gradletest3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // http 요청에 대한 보안을 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/board/**").permitAll()
//                .antMatchers("/board/**").hasRole("user")
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/user/login") // 로그인 페이지
                    .loginProcessingUrl("/user/login")  //Form Action URL과 맞춰야함
                    .defaultSuccessUrl("/board/boardlist") // 성공시 리다이렉트되는 URL
                    .failureUrl("/user/login") // 실패시 리다이렉트되는 URL
                .and()
                .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/user/login")
                    .invalidateHttpSession(true)
                .and()
                    .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService();
    }

    @Bean
    // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
