package com.cqw.springcloud.Controller;

import com.cqw.springcloud.Commons.Entity.CommonResult;
import com.cqw.springcloud.Commons.Entity.Payment;
import com.cqw.springcloud.Service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;
    @GetMapping(value = "/consume/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }
    @GetMapping(value = "/consume/payment/timeout")
    public CommonResult<Payment> TestTime(){
        paymentFeignService.getPaymentTimeout();
        return new CommonResult<>(200,"返回结果",new Payment(20L,"Halo"));
    }
}
