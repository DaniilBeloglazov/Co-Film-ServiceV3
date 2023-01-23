package com.example.cofilmservicev3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;

@EnableWebMvc
@SpringBootApplication
public class CoFilmServiceV3Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CoFilmServiceV3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Runtime runtimeConsole = Runtime.getRuntime();
		runtimeConsole.exec("mkdir -m777 -p images/person/photo");
		runtimeConsole.exec("mkdir -m777 -p images/film");
	}
}
