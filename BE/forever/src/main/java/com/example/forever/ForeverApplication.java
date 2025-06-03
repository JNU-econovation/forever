package com.example.forever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForeverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForeverApplication.class, args);
	}

}
