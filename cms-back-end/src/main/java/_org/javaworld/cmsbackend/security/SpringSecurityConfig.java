package _org.javaworld.cmsbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.csrf().disable();
		http.authorizeRequests()
			    		.antMatchers("/authentication/login").permitAll()
		                .anyRequest().authenticated();

		http.httpBasic();
		http.httpBasic().authenticationEntryPoint(new WsBasicAuthenticationEntryPoint());
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(customAuthenticationProvider);
		auth.eraseCredentials(false);
	}

}
