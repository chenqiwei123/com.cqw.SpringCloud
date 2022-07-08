package com.cqw.springcloud.Controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.cqw.springcloud.Commons.Entity.CommonResult;
import com.cqw.springcloud.Commons.Entity.Payment;
import com.cqw.springcloud.Lb.LoadBalancer;
import com.fasterxml.jackson.core.JsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class OrderController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;
    private static final String Payment_Url="http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(Payment_Url+"/payment/create",payment, CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(Payment_Url+"/payment/get/"+id,CommonResult.class);
    }
    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getEntity(@PathVariable("id") Long id){
        final ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(Payment_Url + "/payment/get/" + id, CommonResult.class);
        log.info(String.valueOf(forEntity));
        return forEntity.getBody();
    }
    @GetMapping(value = "/consumer/payment/lb")
    public String getPayment(){
        final List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances==null||instances.size()<=0){
            return null;
        }
        final ServiceInstance instances1 = loadBalancer.instances(instances);
        return restTemplate.getForObject(instances1.getUri()+"/payment/lb",String.class);
    }
}
