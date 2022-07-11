## StudySpringCloudNotes

---

### 1. 完成服务之间的调用,使用RestTemplate完成客户端调用服务端

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

[官方文档](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)

- **方式一(YML文件配置)**

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

- **方式二(配置类)**

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
