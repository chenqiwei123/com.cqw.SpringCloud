package com.cqw.springcloud.Controller;

import com.cqw.springcloud.Commons.Entity.CommonResult;
import com.cqw.springcloud.Commons.Entity.Payment;
import com.cqw.springcloud.Service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 */
@RestController
@Slf4j
public class PaymentController{
    @Resource
    private PaymentService paymentService;
    @Resource
    private DiscoveryClient discoveryClient;
    @Value("${server.port}")
    private String serverport;
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if(result > 0)
        {
            return new CommonResult(200,"插入数据库成功,serverPort: "+serverport,result);
        }else{
            return new CommonResult(444,"插入数据库失败"+serverport,null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);

        if(payment != null)
        {
            return new CommonResult(200,"查询成功,serverPort:"+serverport,payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID: "+serverport,null);
        }
    }
    @GetMapping(value = "/discoverClient")
    public DiscoveryClient getDiscoveryClient(){
        final List<String> services = discoveryClient.getServices();
        for (int i = 0; i < services.size(); i++) {
            log.info(services.get(i));
        }
        final List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        log.info("啊哈哈哈哈哈!!!!!!!!!!!");
        return serverport;//返回服务接口
    }
    @GetMapping(value = "/payment/timeout")
    public String getPaymentTimeout(){
        log.info("测试超时!!!");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.serverport;
    }
}
