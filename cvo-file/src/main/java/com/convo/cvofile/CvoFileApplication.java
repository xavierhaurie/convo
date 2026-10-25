package com.convo.cvofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = "com.convo") // so that classes in sibling packages will also be scanned
@SpringBootApplication
public class CvoFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvoFileApplication.class, args);
	}

}
