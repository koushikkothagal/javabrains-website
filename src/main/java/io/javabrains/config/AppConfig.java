package io.javabrains.config;

import com.auth0.spring.security.mvc.Auth0Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppConfig extends Auth0Config {

    @Override
    protected void authorizeRequests(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/resources/**","/assets/**", "/blog/**", "/", "/topics/**", "/courses/**", "/lessons/**", "/signup", "/login", "/forgot", "/reset/**", "/terms.html", "/contactus", "/favicon.ico", "/verify/**", "/callback").permitAll()
        .anyRequest().authenticated()
        .antMatchers("/portal/**").authenticated()
        .antMatchers(securedRoute).authenticated();
    }

}


