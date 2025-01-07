package com.example.taskblock;

import com.example.taskblock.model.user.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class TaskBlock {

    public static void main(String[] args) {
        SpringApplication.run(TaskBlock.class, args);
    }

}
