package org.javaworld.cmsbackend;

import javax.sql.DataSource;

import org.javaworld.cmsbackend.util.OsDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfig {

	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Bean
	public CorsFilter corsFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.addExposedHeader("totalPages");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public DataSource getDataSource() throws Exception {
		logger.info("starting creating data source");
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		DataSource ds = null;
		try {
			dataSourceBuilder.url("jdbc:mysql://localhost:3306/cms");
			dataSourceBuilder.username("root");
			dataSourceBuilder.password("System");
			ds = dataSourceBuilder.build();
			ds.getConnection(); // to throw an exception if there is something wrong
		} catch (Exception ex) {
			// prevent using embeded data base in case os is not windows[production env]
			if (!OsDetector.isWindows()) {
				return ds;
			}
			logger.error("an exception occured while creating data source");
			logger.info("using in memory data base instead");
			dataSourceBuilder.driverClassName("org.h2.Driver");
			dataSourceBuilder.url("jdbc:h2:mem:testdb");
			dataSourceBuilder.username("sa");
			dataSourceBuilder.password("");
			ds = dataSourceBuilder.build();
		}
		return ds;
	}

}
