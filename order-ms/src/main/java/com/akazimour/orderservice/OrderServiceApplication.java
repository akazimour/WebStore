package com.akazimour.orderservice;



import com.akazimour.catalogapi.CatalogApi;
import com.akazimour.userapi.api.UserApi;
import hu.webuni.tokenlib.JwtAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackageClasses = {OrderServiceApplication.class, JwtAuthFilter.class, CatalogApi.class})
@EnableFeignClients(basePackageClasses = {UserApi.class, CatalogApi.class})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
