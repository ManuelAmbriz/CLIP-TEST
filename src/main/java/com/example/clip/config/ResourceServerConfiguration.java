package com.example.clip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

    	// Permitir todo sin Token de Auth
    	//allowAll(http);   	
                
    	doubleControllers(http);
    	
    }
    
    public void allowAll(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
		.anyRequest().permitAll();
    }
    
    
    public void doubleControllers(HttpSecurity http) throws Exception {
    	http.authorizeRequests()


    	// Version 2, controllers with security
    	
    	.antMatchers("/api/clip/disbursementProcess").access(SECURED_READ_SCOPE)
		.anyRequest().permitAll();
    }
    
}
