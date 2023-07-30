package com.example.matchmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MatchManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchManagementApplication.class, args);
	}

}
