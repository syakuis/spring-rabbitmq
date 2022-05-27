# Spring RabbitMQ

## AsyncRabbitTemplate

AsyncRabbitTemplate 클래스는 비차단으로 발행을 하면 ListenableFuture 콜백을 받을 수 있다.

구독 속도는 RabbitTemplate 에 비해 매우 느려지는 것 같다.

## 동시적으로 구독하기

- factory.setConcurrentConsumers(20)
- factory.setTaskExecutor(healthCheckTaskExecutor())

위 두가지 설정이 필요하다. 순서를 보장하지 않아도 될때 사용해야 한다.

```
@Bean
fun basicHealthCheckRabbitListenerContainer(): SimpleRabbitListenerContainerFactory {
    val factory = SimpleRabbitListenerContainerFactory()
    factory.setConnectionFactory(connectionFactory)
    factory.setMessageConverter(jsonMessageConverter)
    factory.setConcurrentConsumers(20)
    factory.setTaskExecutor(healthCheckTaskExecutor())
    return factory
}

@Bean("healthCheckTaskExecutor")
fun healthCheckTaskExecutor(): TaskExecutor {
    val executor = ThreadPoolTaskExecutor()
    executor.corePoolSize = 20
    executor.setThreadNamePrefix("healthCheckTE-")
    executor.initialize()
    return executor
}
```