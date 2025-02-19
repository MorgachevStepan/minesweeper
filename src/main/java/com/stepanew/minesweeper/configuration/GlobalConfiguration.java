package com.stepanew.minesweeper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class GlobalConfiguration {

    @Bean
    public Random random() {
        return new Random();
    }

}
