package org.javaworld.cmsbackend;

import org.javaworld.cmsbackend.security.CustomWebSecurityConfigurerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication(
		exclude = 
			{			
				//	SecurityAutoConfiguration.class   // to disable security and make all services unauthenticated
			})
public class CmsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBackEndApplication.class, args);
	}

}
