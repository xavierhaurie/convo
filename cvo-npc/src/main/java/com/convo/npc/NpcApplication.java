package com.convo.npc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = "com.convo") // so that classes in sibling packages will also be scanned
@SpringBootApplication
public class NpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(NpcApplication.class, args);
	}

}
