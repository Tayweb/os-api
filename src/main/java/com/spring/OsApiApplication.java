package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OsApiApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(OsApiApplication.class, args);
	//System.out.println(new BCryptPasswordEncoder().encode("admin"));
	}

	
}
