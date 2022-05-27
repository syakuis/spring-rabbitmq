package io.github.syakuis.rabbit.application

import io.github.syakuis.rabbit.adaptor.out.BasicHealthCheckRabbitProducer
import io.github.syakuis.rabbit.model.ServerInfoDto
import io.github.syakuis.rabbit.model.ServerInfoMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@Service
class BasicHealthCheckService(val healthCheckRabbitProducer: BasicHealthCheckRabbitProducer) {
    private val log = LoggerFactory.getLogger(javaClass)
    fun serverInfo(): ServerInfoDto {
        return ServerInfoDto("1.1.1.1", "테스트 1")
    }

    private fun toMessage(): ServerInfoMessage {
        val serverInfoDto = serverInfo()
        return ServerInfoMessage(serverInfoDto.host, serverInfoDto.name, "2020-01-16 13:45:06.021  WARN 92292 --- [           main] onfigReactiveWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dynamoConfigProperties': @EnableConfigurationProperties or @ConfigurationPropertiesScan must be used to add @ConstructorBinding type io.codebrews.dynamodemo.DynamoConfigProperties\n" +
            "2020-01-16 13:45:06.031  INFO 92292 --- [           main] ConditionEvaluationReportLoggingListener :\n" +
            "Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.\n" +
            "2020-01-16 13:45:06.039 ERROR 92292 --- [           main] o.s.boot.SpringApplication               : Application run failed\n" +
            "org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dynamoConfigProperties': @EnableConfigurationProperties or @ConfigurationPropertiesScan must be used to add @ConstructorBinding type io.codebrews.dynamodemo.DynamoConfigProperties\n" +
            " at org.springframework.boot.context.properties.ConfigurationPropertiesBeanDefinitionValidator.validate(ConfigurationPropertiesBeanDefinitionValidator.java:66) ~[spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.boot.context.properties.ConfigurationPropertiesBeanDefinitionValidator.postProcessBeanFactory(ConfigurationPropertiesBeanDefinitionValidator.java:45) ~[spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:286) ~[spring-context-5.2.2.RELEASE.jar:5.2.2.RELEASE]\n" +
            " at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:174) ~[spring-context-5.2.2.RELEASE.jar:5.2.2.RELEASE]\n" +
            " at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:706) ~[spring-context-5.2.2.RELEASE.jar:5.2.2.RELEASE]\n" +
            " at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:532) ~[spring-context-5.2.2.RELEASE.jar:5.2.2.RELEASE]\n" +
            " at org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.refresh(ReactiveWebServerApplicationContext.java:66) ~[spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:747) [spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397) [spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.boot.SpringApplication.run(SpringApplication.java:315) [spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226) [spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215) [spring-boot-2.2.2.RELEASE.jar:2.2.2.RELEASE]\n" +
            " at io.codebrews.dynamodemo.DynamodemoApplicationKt.main(DynamodemoApplication.kt:15) [classes/:na]\n" +
            "Process finished with exit code 1")
    }

    fun run(limit: Int) {
        log.info("[로그] 헬스 체크를 위한 호스트 정보를 준비중입니다.")
        for (i in 0..limit) {
            healthCheckRabbitProducer.serverInfo(toMessage())
        }
        log.info("[로그] 헬스 체크를 위한 호스트 정보를 발행했습니다.")
    }

    fun asyncRun(limit: Int) {
        log.info("[로그] 헬스 체크를 위한 호스트 정보를 준비중입니다.")
        for (i in 0..limit) {
            healthCheckRabbitProducer.asyncServerInfo(toMessage())
        }
        log.info("[로그] 헬스 체크를 위한 호스트 정보를 발행했습니다.")
    }
}