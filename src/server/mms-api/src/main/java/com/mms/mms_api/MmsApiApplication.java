package com.mms.mms_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MmsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmsApiApplication.class, args);
	}

}
