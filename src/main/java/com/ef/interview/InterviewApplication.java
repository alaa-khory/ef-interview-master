package com.ef.interview;

import com.ef.interview.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewApplication {

	public static void main(String[] args) {

//		new UserService().prepareUser();

		SpringApplication.run(InterviewApplication.class, args);
	}

}
