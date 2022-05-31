package com.example.translateapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class TranslateAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslateAppApplication.class, args);
    }

}
