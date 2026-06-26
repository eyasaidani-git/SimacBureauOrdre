package com.simac.simacordre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimacOrdreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimacOrdreApplication.class, args);
    }
}