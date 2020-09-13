package com.maslycht.gameplayclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GameplayClientApp {
    public static void main(String[] args) {
        SpringApplication.run(GameplayClientApp.class, args);
    }
}
