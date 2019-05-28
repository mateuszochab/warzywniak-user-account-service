package com.ochabmateusz.warzywniak.warzywniakuseraccountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarzywniakUserAccountServiceApplication {

	public static void main(String[] args) {



		SpringApplication.run(WarzywniakUserAccountServiceApplication.class, args);




	}

}
//TODO to finish service: admin controller, finish units tests, add integration tests,
//TODO connect service to config server, discovery, cache database, tracing server