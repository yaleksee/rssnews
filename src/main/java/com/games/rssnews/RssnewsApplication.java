package com.games.rssnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RssnewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssnewsApplication.class, args);
    }

}
