package com.bank.AndrejsBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AndrejsBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AndrejsBankApplication.class, args);
	}

}

// Selenium automated tests
// Microservices
// SQL
//./gradlew build
// sudo docker build -t bank .
//sudo docker tag bank andrejsmatvejevs/bank
//sudo docker push andrejsmatvejevs/bank