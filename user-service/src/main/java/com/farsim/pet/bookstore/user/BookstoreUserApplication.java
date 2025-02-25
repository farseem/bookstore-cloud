package com.farsim.pet.bookstore.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreUserApplication {

	public static void main(String[] args) {
		System.out.println("Active Profile: " + System.getProperty("spring.profiles.active"));
		SpringApplication.run(BookstoreUserApplication.class, args);
	}

}
