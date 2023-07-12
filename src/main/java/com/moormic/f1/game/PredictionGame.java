package com.moormic.f1.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.moormic.f1"})
public class PredictionGame {

    public static void main(String[] args) {
        SpringApplication.run(PredictionGame.class, args);
    }

}
