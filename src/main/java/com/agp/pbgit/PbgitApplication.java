package com.agp.pbgit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.agp.pbgit.service.db")
public class PbgitApplication {

	public static void main(String[] args) {
		SpringApplication.run(PbgitApplication.class, args);
	}
}
