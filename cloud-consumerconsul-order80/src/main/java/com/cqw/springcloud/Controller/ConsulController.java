package com.cqw.springcloud.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.annotation.Resources;

@RestController
public class ConsulController {
    @Resource
    private RestTemplate restTemplate;
    private String ConsulUrl="http://ConsulProvider8006";
    @GetMapping("/Consul/PaymentOrder")
    public String GetConsulInfo(){
        final String forObject = restTemplate.getForObject(ConsulUrl + "/payment/consul", String.class);
        return forObject;
    }
}
