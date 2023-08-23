package com.akazimour.catalogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CatalopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalopApiApplication.class, args);
	}

}
