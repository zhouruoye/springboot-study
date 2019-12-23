package com.cest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springboot 定时任务
 */
@EnableScheduling
@SpringBootApplication
public class SpringbootTimingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTimingApplication.class, args);
	}

}
