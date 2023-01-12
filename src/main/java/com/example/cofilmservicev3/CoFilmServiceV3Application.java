package com.example.cofilmservicev3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class CoFilmServiceV3Application {

	public static void main(String[] args) {
		SpringApplication.run(CoFilmServiceV3Application.class, args);
	}

}
