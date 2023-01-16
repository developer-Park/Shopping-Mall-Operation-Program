package com.example.secondteamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//
public class SecondTeamProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondTeamProjectApplication.class, args);
    }

}
