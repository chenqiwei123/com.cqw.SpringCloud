package com.cqw.springcloud.Config;


import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ApplicationContextConfig
{
    @Bean
//    @LoadBalanced
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
    @Bean
    public LoadBalancerClientFactory getloadBalancerClientFactory(){
        return new LoadBalancerClientFactory();
    }

}

