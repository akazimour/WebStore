package com.akazimour.catalogservice;

import com.akazimour.catalogservice.service.InitDbService;
import hu.webuni.tokenlib.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackageClasses = {CatalogServiceApplication.class, JwtAuthFilter.class})
@EnableCaching
public class CatalogServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}
    @Autowired
	InitDbService initDbService;
	@Override
	public void run(String... args) throws Exception {
		initDbService.deleteDb();
		initDbService.deleteAudTables();
		initDbService.createCategories();
	}
}
