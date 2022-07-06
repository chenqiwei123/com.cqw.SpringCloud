package com.cqw.springcloud.Controller;

import com.cqw.springcloud.Entity.CommonResult;
import com.cqw.springcloud.Entity.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
@RestController
public class OrderController {
    @Resource
    private RestTemplate restTemplate;
    private static final String Payment_Url="http://localhost:8001";

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(Payment_Url+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(Payment_Url+"/payment/get/"+id,CommonResult.class);
    }
}
