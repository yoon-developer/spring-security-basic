package com.spring.securitybasic.config;

import com.spring.securitybasic.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PrincipalOauth2UserService principalOauth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/user/**").authenticated()
        .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or  hasRole('ROLE_MANAGER')")
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .anyRequest().permitAll()
    .and()
        .formLogin()
        .loginPage("/loginForm")
        .usernameParameter("account")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/")
    .and()
        .oauth2Login()
        .loginPage("/loginForm")
        .userInfoEndpoint()
        .userService(principalOauth2UserService);
  }
}
