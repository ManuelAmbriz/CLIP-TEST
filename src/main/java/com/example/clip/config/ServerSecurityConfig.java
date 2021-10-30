package com.example.clip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Order( SecurityProperties.BASIC_AUTH_ORDER - 2 )
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder userPasswordEncoder;
	
	
	@Bean
    @Override
    public UserDetailsService userDetailsService () {

    UserDetails user = User.builder().username("user").password(userPasswordEncoder.encode("secret")).roles("USER").build();
    UserDetails userAdmin = User.builder().username("admin").password(userPasswordEncoder.encode("secret")).roles("ADMIN").build();
    
        return new InMemoryUserDetailsManager (user, userAdmin);
    }
	
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(userPasswordEncoder);;
    }
}
