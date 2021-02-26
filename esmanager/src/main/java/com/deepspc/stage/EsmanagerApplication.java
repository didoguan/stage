package com.deepspc.stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EsmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsmanagerApplication.class, args);
	}

}
