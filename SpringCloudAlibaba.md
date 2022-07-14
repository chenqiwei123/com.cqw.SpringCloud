# Naocs

---
[官方文档](https://github.com/alibaba/nacos)

[官方网站](https://nacos.io/zh-cn/)

### Nacos provides four major functions
- **服务发现和服务健康检查**

Nacos 使服务可以轻松注册自己并通过 DNS 或 HTTP 接口发现其他服务。Nacos 还提供服务的实时健康检查，以防止向不健康的主机或服务实例发送请求。
- **动态配置管理**

动态配置服务允许您在所有环境中以集中和动态的方式管理所有服务的配置。Nacos 消除了在更新配置时重新部署应用程序和服务的需要，这使得配置更改更加高效和敏捷。
- **动态 DNS 服务**

Nacos 支持加权路由，让您在数据中心内的生产环境中更轻松地实现中间层负载均衡、灵活的路由策略、流量控制和简单的 DNS 解析服务。它可以帮助您轻松实现基于 DNS 的服务发现，并防止应用程序耦合到特定于供应商的服务发现 API。
- **服务和元数据管理**

Nacos 提供易于使用的服务仪表板，帮助您管理服务元数据、配置、kubernetes DNS、服务健康和指标统计。

### Deploying Nacos on cloud 

- **第一步：下载二进制包**

您可以从最新的稳定版本下载该软件包。 以发布nacos-server-1.0.0.zip为例：
```shell
unzip nacos-server-1.0.0.zip
cd nacos/bin
```
- **第 2 步：启动服务器**

在Linux/Unix/Mac平台上，运行以下命令以独立模式启动服务器：
```shell
#注意对应官网要的maven最低版本要求和jdk的最低版本要求
sh startup.sh -m standalone
```
在Windows平台上，运行以下命令以独立模式启动服务器。或者，也可以双击startup.cmd运行 NacosServer。
```shell
startup.cmd -m standalone
```
有关更多详细信息，请参阅[快速入门](https://nacos.io/en-us/docs/quick-start.html)。

**其他开源项目的快速入门**

[Nacos 命令和控制台快速入门](https://nacos.io/en-us/docs/quick-start.html)

[dubbo 快速入门](https://nacos.io/en-us/docs/use-nacos-with-dubbo.html)

[Spring Cloud 快速入门](https://nacos.io/en-us/docs/quick-start-spring-cloud.html)

[Kubernetes 快速入门](https://nacos.io/en-us/docs/use-nacos-with-kubernetes.html)

**本文示例代码模块说明**

|模块名|备注|
|---|---|
|cloudalibaba-provider-payment9001|服务提供者注册到nacos中|
|cloudalibaba-provider-payment9002|服务提供者注册到nacos中|
|cloudalibaba-consumer-nacos-order83|服务调用者,调用nacos注册中心的服务|

**如果你用的是最新Nacos版本**

以这个版本为例:[Spring Cloud Alibaba 2021.0.1.0](https://spring-cloud-alibaba-group.github.io/github-pages/2021/en-us/index.html#_spring_cloud_alibaba_nacos_discovery)

- 有可能你会出现这个错误提示
```shell
A component required a bean of type 'org.springframework.cloud.client.loadbalancer.LoadBalancerClient' that could not be found.
```

- **原因**

Alibaba-nacos-discovery取消了netflix的Ribbon负载均衡,那么如何使用`LoadBalancer`来看看LoadBalancer的实现类

```java
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(RestTemplate.class) 
	@Conditional(OnNoRibbonDefaultCondition.class)  
	protected static class BlockingLoadbalancerClientConfig {

		@Bean
		@ConditionalOnBean(LoadBalancerClientFactory.class)
		@Primary
		public BlockingLoadBalancerClient blockingLoadBalancerClient(
				LoadBalancerClientFactory loadBalancerClientFactory) {
			return new BlockingLoadBalancerClient(loadBalancerClientFactory);
		}

	}
```

**看来源码便知,自定义一个LoadBalancerClientFactory对象注入Bean工厂**
```java
@Configuration
public class ApplicationContextConfig
{
    @Bean
//    @LoadBalanced //使用netflix的注入
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
    @Bean
    public LoadBalancerClientFactory getloadBalancerClientFactory(){
        return new LoadBalancerClientFactory();
    }

}
```
