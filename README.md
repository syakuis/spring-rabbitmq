# Spring RabbitMQ

## prefetch count

기본 값 250

하나의 컨슈머가 처리할 수 있는 일의 량

## AsyncRabbitTemplate

AsyncRabbitTemplate 클래스는 비차단으로 발행되며 ListenableFuture 클래스로 콜백받을 수 있다.

발행과 구독을 동시에 처리하므로 RabbitTemplate 에 비해 지연이 많이 발생된다.

## 동시적으로 구독하기

동시적으로 구독할 갯수를 설정하고 `factory.setConcurrentConsumers(20)` 구독 후 처리에 대한 비동기 스레드 풀을 `factory.setTaskExecutor(healthCheckTaskExecutor())` 설정한다.

동시적으로 구독할 경우 순서를 보장하지 않는 다.

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