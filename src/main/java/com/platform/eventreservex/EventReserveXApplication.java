package com.platform.eventreservex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventReserveXApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventReserveXApplication.class, args);
    }

}
