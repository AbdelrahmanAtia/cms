package org.javaworld.cmsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })

public class CmsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBackEndApplication.class, args);
	}

}
