package io.javabrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan(basePackages = {"io.javabrains", "com.auth0.spring.security.mvc"})
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:auth0.properties")
})
@EnableCaching
public class JavabrainsApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(JavabrainsApplication.class, args);
	}
	

}
