package com.cqw.springcloudAlibaba.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class NacosController {
    @Value("${server.port}")
    private String serverPort;
    @GetMapping(value = "/payment/nacos/{id}")
    public String echo(@PathVariable("id") Long id) {
        return "Hello Nacos Discovery ID: " + id+"\t 端口: "+this.serverPort;
    }
}
