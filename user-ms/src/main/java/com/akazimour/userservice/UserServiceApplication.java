package com.akazimour.userservice;

import com.akazimour.userapi.api.UserApi;
import com.akazimour.userservice.service.InitDbService;
import hu.webuni.tokenlib.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, UserServiceApplication.class, UserApi.class})
public class UserServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
@Autowired
	InitDbService initDbService;

	@Override
	public void run(String... args) throws Exception {

		initDbService.deleteUsers();
		initDbService.createUserProfile();


	}
}
