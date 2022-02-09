package com.iesfranciscodelosrios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.iesfranciscodelosrios.model")
public class ExpressprintApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpressprintApplication.class, args);
	}

}
