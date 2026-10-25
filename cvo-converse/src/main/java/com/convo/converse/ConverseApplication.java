package com.convo.converse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = "com.convo") // so that classes in sibling packages will also be scanned
@SpringBootApplication
public class ConverseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverseApplication.class, args);
    }

}
