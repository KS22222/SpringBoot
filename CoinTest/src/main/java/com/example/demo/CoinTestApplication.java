package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.demo","com.example.model","com.example.controller","com.example.repository"})
@EntityScan({"com.example.demo","com.example.model","com.example.controller","com.example.repository"})
@EnableJpaRepositories({"com.example.demo","com.example.model","com.example.controller","com.example.repository"})
public class CoinTestApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CoinTestApplication.class, args);
	}
}