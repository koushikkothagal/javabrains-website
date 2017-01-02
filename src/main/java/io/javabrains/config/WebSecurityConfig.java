package io.javabrains.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import io.javabrains.security.UserRepositoryUserDetailsService;
import io.javabrains.web.home.AppErrorController;

// @Configuration
// @EnableWebSecurity
public class WebSecurityConfig  {
	/*
	@Autowired
	UserRepositoryUserDetailsService userDetailsService;
	
	@Autowired
    BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	PersistentTokenRepository persistentTokenRepository;
	
	@Autowired
	AbstractRememberMeServices rememberMeServices;

	@Override
	public void configure(WebSecurity web) throws Exception {
	     web.ignoring()
	        .antMatchers("/resources/**");
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		//.rememberMe()
		//	.rememberMeParameter("rememberme")
		//	.rememberMeServices(rememberMeServices)
			
			
		// .and()
        .authorizeRequests()
            .antMatchers("/resources/**","/assets/**", "/blog/**", "/", "/topics/**", "/courses/**", "/lessons/**", "/signup", "/login", "/forgot", "/reset/**", "/terms.html", "/contactus", "/favicon.ico", "/verify/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .rememberMe().rememberMeParameter("rememberme").tokenRepository(persistentTokenRepository).tokenValiditySeconds(2592000)
            .and()
        .logout()
            .permitAll();
    }
	
	@Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.userDetailsService(userDetailsService)
        	.passwordEncoder(bcryptEncoder);
    }
	
	@Bean
    public BCryptPasswordEncoder findBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean   
	  public AbstractRememberMeServices rememberMeServices() {
	      PersistentTokenBasedRememberMeServices rememberMeServices =
	          new PersistentTokenBasedRememberMeServices("JavaBrains", userDetailsService(), persistentTokenRepository);
	      rememberMeServices.setAlwaysRemember(true);
	      //rememberMeServices.setTokenLength(1209600);
	      rememberMeServices.setCookieName("remember-me-posc");
	      rememberMeServices.setTokenValiditySeconds(2592000);
	      return rememberMeServices;
	  }
	
	@Autowired
	 private ErrorAttributes errorAttributes;

	 @Bean
	 public AppErrorController appErrorController(){return new AppErrorController(errorAttributes);}
	
	*/
}
