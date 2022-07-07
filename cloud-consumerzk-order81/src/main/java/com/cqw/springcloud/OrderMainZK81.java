package com.cqw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderMainZK81 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainZK81.class,args);
    }
}
