package com.example.taskblock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class TaskBlockApp {

    public static void main(String[] args) {
        SpringApplication.run(TaskBlockApp.class, args);
    }

}
