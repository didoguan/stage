package com.deepspc.stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataplatformApplication.class, args);
	}

}
