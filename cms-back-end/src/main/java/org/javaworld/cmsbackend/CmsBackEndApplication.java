package org.javaworld.cmsbackend;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties.Settings;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseConfigurer;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

@SpringBootApplication(exclude = {
		//DataSourceAutoConfiguration.class
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

	public String getLogsFilesPath() {
		return getProjectFilesLocation() + File.separator + "logs";
	}

}
