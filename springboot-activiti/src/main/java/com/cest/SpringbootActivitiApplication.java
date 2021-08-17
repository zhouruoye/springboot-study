package com.cest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springboot 流程
 */
@SpringBootApplication
@EnableAsync
public class SpringbootActivitiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootActivitiApplication.class, args);
	}

}
