package com.example.debeziumdebug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DebeziumDebugApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebeziumDebugApplication.class, args);
    }

}
