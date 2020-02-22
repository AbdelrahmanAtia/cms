package org.javaworld.cmsbackend.security;

import org.javaworld.cmsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserService userService; 
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {	
		
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests()
							.antMatchers(HttpMethod.OPTIONS).permitAll()  // no need for authorization token for options request
							.antMatchers("/api/authentication/login").permitAll()
					.anyRequest().authenticated()
					.and().httpBasic(); // basic authentication
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Set your configuration on the auth object
		
		/*
		auth.inMemoryAuthentication().withUser("blah")
							         .password("blah")
							         .roles("USER")
							         .and()
							         .withUser("foo")
							         .password("foo")
							         .roles("ADMIN");
		 */
		
		auth.userDetailsService(userService);
	}
	
	
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    

}
