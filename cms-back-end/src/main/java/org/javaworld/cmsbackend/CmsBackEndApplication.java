package org.javaworld.cmsbackend;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		// SecurityAutoConfiguration.class // to disable security and make all services
		// unauthenticated
})
public class CmsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBackEndApplication.class, args);
	}

	public String getProjectName() {
		return "cms";
	}

	public String getProjectFilesLocation() {
		return System.getProperty("user.home") + File.separator + "app_configs" + File.separator
				+ this.getProjectName();
	}

}
