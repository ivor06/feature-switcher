package com.example.featureswitcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.reactive.config.EnableWebFlux;
//
//@EnableWebFlux
@SpringBootApplication
@EnableTransactionManagement
public class FeatureswitcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureswitcherApplication.class, args);
	}

}
