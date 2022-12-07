package com.example.gradletest3.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // http 요청에 대한 보안을 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
//                .antMatchers("/user/**").permitAll()
//                .antMatchers("/board/**").permitAll()
//                .antMatchers("/*").permitAll()
//                .antMatchers("/dust").permitAll()
//                .antMatchers("/movie/**").hasRole("user")
////                .antMatchers("/board/**").hasRole("user")
////                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/static/js/**").permitAll()
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
//        auth.inMemoryAuthentication().withUser()
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
