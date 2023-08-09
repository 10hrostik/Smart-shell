package com.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.api.repositories")
@ComponentScan(basePackages = "com.api")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SmartServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartServerApplication.class, args);
	}

}
