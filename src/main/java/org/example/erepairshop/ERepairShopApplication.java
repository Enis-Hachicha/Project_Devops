package org.example.erepairshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.erepairshop.repositores")
@EntityScan(basePackages = "org.example.erepairshop.entities")
public class ERepairShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(ERepairShopApplication.class, args);
	}
}
