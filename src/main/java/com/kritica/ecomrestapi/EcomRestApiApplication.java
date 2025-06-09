package com.kritica.ecomrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kritica"})
@EntityScan("com.kritica.model")  // Add this to scan your entities package
@EnableJpaRepositories("com.kritica.repository") // Add this if you have repositories

public class EcomRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomRestApiApplication.class, args);
	}

}
