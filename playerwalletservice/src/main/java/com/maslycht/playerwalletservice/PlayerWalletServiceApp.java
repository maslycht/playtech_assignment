package com.maslycht.playerwalletservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlayerWalletServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PlayerWalletServiceApp.class, args);
    }

}
