package com.shadath.overview.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan({"com.shadath.overview"})
@EntityScan("com.shadath.overview.domain")
@EnableJpaRepositories("com.shadath.overview.repository")
public class OverviewApplication {
	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		try {
			OverviewApplication.applicationContext = SpringApplication.run(OverviewApplication.class, args);
		}
		finally {
			System.out.println("Application is running...");
		}
	}
}
