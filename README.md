## StudySpringCloudNotes
### 模块说明
|模块名称|模块作用介绍说明|
|---|---|
|cloud-consumer-order80| 消费者调用Eureka注册中心提供者 |
|cloud-api-commons| 自定义公共实体类 |
|cloud-eureka-server7001| Eureka注册中心7001端口|
|cloud-eureka-server7002| Eureka注册中心7002端口|
|cloud-provider-payment8002| 服务提供者注册到Eureka注册中心 |
|cloud-provider-payment8001| 服务提供者注册到Eureka注册中心 |
|cloud-provider-payment8004| 服务提供者注册到Zookeeper注册中心|
|cloud-consumerzk-order81| 消费者调用Zookeeper注册中心提供者 |
|ConsulProvider8006| 务提供者注册到Consul注册中心 |
|cloud-consumerconsul-order80| 消费者调用Consul注册中心提供者 |
|cloud-consumer-feign-order80| 实现负载均衡调用方|
|cloud-provider-hygtrix-payment8001|实现服务降级、服务熔断 |
|cloud-consumer-feign-hystrix-order80| OpenFeign服务降级调用|
|cloud-consumer-hystrix-dashboard9001| Hystrix图形化Dashboard搭建|
|cloud-gateway-gateway9527| 网关GateWay进行predicate(断言)、Filter|
|cloud-config-center-3344| config服务端 |
|cloud-config-client-3355| config客户端|
|cloud-config-client-3366| config客户端|
|cloud-stream-rabbitmq-provider8801|stream生产者 |
|cloud-stream-rabbitmq-consumer8802|stream消费者 |
|cloud-stream-rabbitmq-consumer8803|stream消费者|
---
- [StudySpringCloudNotes](#studyspringcloudnotes)
  * [1.Use the RestTemplate to call the server from the client](#1use-the-resttemplate-to-call-the-server-from-the-client)
  * [2.Eureka cluster code implementation](#2eureka-cluster-code-implementation)
  * [3.Implement the Zookeeper and Consul service invocation](#3implement-the-zookeeper-and-consul-service-invocation)
  * [4.Customize Ribbon load rules](#4customize-ribbon-load-rules)
  * [5.OpenFeign service invocation and OpenFeign timeout control](#5openfeign-service-invocation-and-openfeign-timeout-control)
  * [6.OpenFeign](#6openfeign)
  * [7.Hystrix](#7hystrix)
  * [8.Gateway](#8gateway)
    + [8.1 GateWay_Predicate](#81-gateway-predicate)
    + [8.2 GateWay_Filter](#82-gateway-filter)
  * [9.Spring Cloud Config](#9spring-cloud-config)
    + [9.1.Spring Cloud Config Server](#91spring-cloud-config-server)
    + [步骤:](#---)
    + [9.1.Spring Cloud Config Client](#91spring-cloud-config-client)
  * [10.SpringCloud Bus RabbitMQ](#10springcloud-bus-rabbitmq)
  * [11.SpringCloud Stream](#11SpringCloud-Stream)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

### 1.Use the RestTemplate to call the server from the client

---

### 2.Eureka cluster code implementation

---

### 3.Implement the Zookeeper and Consul service invocation

---

### 4.Customize Ribbon load rules

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

### 5.OpenFeign service invocation and OpenFeign timeout control

---
### 6.OpenFeign

- **NONE:**  默认的，不显示任何日志;
- **BASIC:**  仅记录请求方法、URL、响应状态码及执行时间;
- **HEADERS:**  除了BASIC中定义的信息之外，还有请求和响应的头信息;
- **FULL:**  除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。
---
### 7.Hystrix

[官网介绍](https://github.com/Netflix/Hystrix/wiki/How-it-Works)

**大神结论:**

[Martin Fowler的相关论文](https://martinfowler.com/bliki/CircuitBreaker.html)

- **服务降级及示例**
- **服务熔断及示例**
- **服务限流及示例**
- **Hystrix图形化Dashboard搭建**`cloud-consumer-hystrix-dashboard9001`
---

### 8.Gateway

[官方文档](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-request-predicates-factories)

#### 8.1 GateWay_Predicate

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

#### 8.2 GateWay_Filter

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

#### 9.1.Spring Cloud Config Server

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


#### 9.1.Spring Cloud Config Client

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

### 10.SpringCloud Bus RabbitMQ

- **安装Erlang**
  [下载地址](http://erlang.org/download/otp_win64_21.3.exe)

- **安装RabbitMQ**
  [下载地址](https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.3/rabbitmq-server-3.8.3.exe)

- **安装RabbitMQ插件**

```shell
#进入到rabbitmq安装目录的sbin目录下
cd C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.3\sbin
#安装插件
.\rabbitmq-plugins.bat enable rabbitmq_management
```
- **可视化插件**

1. 访问地址查看是否安装成功：[http://localhost:15672/](http://localhost:15672/)

2. 输入账号密码并登录：guest guest

- **给Config Server添加依赖**

```xml
        <!--添加消息总线RabbitNQ支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

```
- **添加rabbitmq配置文件**
```yaml
#rabbitmq相关配置<--------------------------
rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    
##rabbitmq相关配置,暴露bus刷新配置的端点<--------------------------
management:
  endpoints: #暴露bus刷新配置的端点
    web:
      exposure:
        include: 'bus-refresh'
```

- 更改github内容,修改版本号,如果配置文件映射的是本地拉取的github文件,则pull一下.发送POST请求`curl -X POST "http://localhost:3344/actuator/bus-refresh"`
> 出现405错误重启IDEA!,即可实现一次修改,广播通知,处处生效

### 11.SpringCloud Stream

**常见MQ(消息中间件)**
- ActiveMQ
- RabbitMQ
- RocketMQ
- Kafka

**Stream是什么及Binder介绍**

[官方文档1](https://spring.io/projects/spring-cloud-stream#overview)

[官方文档2](https://cloud.spring.io/spring-tloud-static/spring-cloud-stream/3.0.1.RELEASE/reference/html/Spring)

[Cloud Stream中文指导手册](https://m.wang1314.com/doc/webapp/topic/20971999.html)

|组成|	说明|
|---|---|
|Middleware	|中间件，目前只支持RabbitMQ和Kafka|
|Binder|	Binder是应用与消息中间件之间的封装，目前实行了Kafka和RabbitMQ的Binder，通过Binder可以很方便的连接中间件，可以动态的改变消息类型(对应于Kafka的topic,RabbitMQ的exchange)，这些都可以通过配置文件来实现|
|@Input	|注解标识输入通道，通过该输乎通道接收到的消息进入应用程序|
|@Output	|注解标识输出通道，发布的消息将通过该通道离开应用程序|
|@StreamListener|	监听队列，用于消费者的队列的消息接收|
|@EnableBinding	|指信道channel和exchange绑定在一起|

**代码中的五个模块测试**

|模块名|模块职责说明|
|---|---|
|cloud-eureka-server7001           |  注册中心|
|cloud-eureka-server7002           |  注册中心|
|cloud-stream-rabbitmq-provider8801| 作为生产者进行发消息模块|
|cloud-stream-rabbitmq-consumer8802| 作为消息接收模块|
|cloud-stream-rabbitmq-consumer8803| 作为消息接收模块|

**Stream之group解决消息重复消费**

- **原理**

> 微服务应用放置于同一个group中，就能够保证消息只会被其中一个应用消费一次。 不同的组是可以重复消费的，同一个组内会发生竞争关系，只有其中一个可以消费。 8802/8803都变成不同组，group两个不同

- **解决消息重复消费**

默认每个组的组号不一致,设置相同组的组号一样就好了 `spring.cloud.stream.bindings.output.group=Group` 
