## StudySpringCloudNotes

---
- [StudySpringCloudNotes](#studyspringcloudnotes)
  * [1.完成服务之间的调用,使用RestTemplate完成客户端调用服务端](#1------------resttemplate----------)
  * [2. Eureka集群的代码实现(注册中心:7001,7002;服务:8001,8002;客服端:81)](#2-eureka-------------7001-7002----8001-8002-----81-)
  * [3. Zookeeper和consul服务调用实现](#3-zookeeper-consul------)
  * [4. 定制化设置Ribbon负载规则](#4------ribbon----)
  * [5.OpenFeign服务调用及OpenFeign超时控制](#5openfeign-----openfeign----)
  * [6.OpenFeign日志增强](#6openfeign----)
  * [7.Hystrix介绍](#7hystrix--)
  * [8.Gateway配置路由](#8gateway----)
    + [8.1 GateWay常用的Predicate](#81-gateway---predicate)
    + [8.2 GateWay的Filter](#82-gateway-filter)
  * [9.Spring Cloud Config](#9spring-cloud-config)
    + [9.1.Spring Cloud Config Server配置](#91spring-cloud-config-server--)
    + [步骤:](#---)
    + [9.1.Spring Cloud Config Client配置](#91spring-cloud-config-client--)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

### 1.完成服务之间的调用,使用RestTemplate完成客户端调用服务端

---

### 2. Eureka集群的代码实现(注册中心:7001,7002;服务:8001,8002;客服端:81)

---

### 3. Zookeeper和consul服务调用实现

---

### 4. 定制化设置Ribbon负载规则

- **RoundRobinRule:** 轮询  
  <br />

- **RandomRule:** 随机

    <br />

- **RetryRule:** 先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重

    <br />

- **WeightedResponseTimeRule:** 对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择

    <br />

- **BestAvailableRule:** 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务

    <br />

- **AvailabilityFilteringRule:** 先过滤掉故障实例，再选择并发较小的实例

    <br />

- **ZoneAvoidanceRule:** 默认规则,复合判断server所在区域的性能和server的可用性选择服务器

---

### 5.OpenFeign服务调用及OpenFeign超时控制

---
### 6.OpenFeign日志增强

- **NONE:**  默认的，不显示任何日志;
- **BASIC:**  仅记录请求方法、URL、响应状态码及执行时间;
- **HEADERS:**  除了BASIC中定义的信息之外，还有请求和响应的头信息;
- **FULL:**  除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。
---
### 7.Hystrix介绍

[官网介绍](https://github.com/Netflix/Hystrix/wiki/How-it-Works)

**大神结论:**

[Martin Fowler的相关论文](https://martinfowler.com/bliki/CircuitBreaker.html)

- **服务降级及示例**
- **服务熔断及示例**
- **服务限流及示例**
- **Hystrix图形化Dashboard搭建**`cloud-consumer-hystrix-dashboard9001`
---

### 8.Gateway配置路由

[官方文档](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-request-predicates-factories)

#### 8.1 GateWay常用的Predicate

-  **方式一: YML文件配置**

```yaml
server:
  port: 9527

spring:
  application:
    name: cloud-gateway
#############################新增网关配置###########################
  cloud:
    gateway:
      routes:
        - id: payment_routh #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: http://localhost:8001          #匹配后提供服务的路由地址
          #uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**         # 断言，路径相匹配的进行路由

        - id: payment_routh2 #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: http://localhost:8001          #匹配后提供服务的路由地址
          #uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/**         # 断言，路径相匹配的进行路由
####################################################################

eureka:
  instance:
    hostname: cloud-gateway-service
  client: #服务提供者provider注册进eureka服务列表内
    service-url:
      register-with-eureka: true
      fetch-registry: true
      defaultZone: http://eureka7001.com:7001/eureka

```

- **方式二 : 配置类**

```java
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GateWayConfig
{
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder)
    {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("HaloWay",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();

        return routes.build();
    }
}

```

#### 8.2 GateWay的Filter

[官方文档](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gatewayfilter-factories)

- **自定义过滤配置类**

```java

package com.cqw.springcloud.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GateWayConfig
{
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder)
    {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("HaloWay",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();

        return routes.build();
    }
}
```

**浏览器输入:**
```textmate
http://localhost:9527/payment/lb - 访问异常
http://localhost:9527/payment/lb?uname=abc - 正常访问
```
---

### 9.Spring Cloud Config

#### 9.1.Spring Cloud Config Server配置

[官方文档](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_spring_cloud_config_server)

#### 步骤:

- **1.在GitHub新建一个仓库,并clone到本地**
```shell
git init 
git clone https://github.com/chenqiwei123/springcloudConfig
```

- **2.添加依赖**
```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-config-server</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
```

- **3.application.yml配置文件**

```yaml
server:
  port: 3344

spring:
  application:
    name:  cloud-config-center #注册进Eureka服务器的微服务名
  cloud:
    config:
      server:
        git:
          uri: file:///D:/Git/SpringConfig/springcloudConfig #GitHub上面的git仓库名字
      ####读取分支
      label: master

#服务注册到eureka地址
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
    register-with-eureka: false
    fetch-registry: false
```

- **4.主启动类添加`@EnableConfigServer`**

```java
package com.cqw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCenterMain3344
{
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}
```

- **5.启动注册中心并测试**

1.我的注册中心是7001,启动它

2.访问:[http://127.0.0.1:3344/master/config-dev.yml](http://127.0.0.1:3344/master/config-dev.yml)

**页面返回结果:**

```textmate
config:
  info: master branch,springcloud-config/config-dev.yml version=7
```

**配置读取规则**

[官方文档](https://docs.spring.io/spring-cloud-config****/docs/current/reference/html/#_quick_start)


#### 9.1.Spring Cloud Config Client配置

[官方文档](https://docs.spring.io/spring-cloud-config/docs/2.2.8.RELEASE/reference/html/#_spring_cloud_config_client)

- **添加Config Client依赖**

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

```

- **Config First Bootstrap(`bootstrap.yml`)**

```yaml
server:
  port: 3355

spring:
  application:
    name: config-client
  cloud:
    #Config客户端配置
    config:
      label: master #分支名称
      name: config #配置文件名称
      profile: dev #读取后缀名称   上述3个综合：master分支上config-dev.yml的配置文件被读取http://config-3344.com:3344/master/config-dev.yml
      uri: http://localhost:3344 #配置中心地址k


#服务注册到eureka地址
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka

# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: "*"
```
- **TEST读取并动态刷新**
```java
@RestController
@RefreshScope
public class ConfigClientController
{
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo()
    {
        return configInfo;
    }
}
```

- **启动 注册中心、config服务端、config客户端**

修改github中文件,并手动post属性config客服端 `curl -X POST "http://localhost:3355/actuator/refresh"`
