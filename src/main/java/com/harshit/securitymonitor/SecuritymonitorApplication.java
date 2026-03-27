package com.harshit.securitymonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.harshit.securitymonitor")
public class SecuritymonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritymonitorApplication.class, args);
    }
}