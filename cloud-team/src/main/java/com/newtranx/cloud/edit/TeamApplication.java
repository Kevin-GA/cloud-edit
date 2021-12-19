package com.newtranx.cloud.edit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TeamApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamApplication.class, args);
    }
}
