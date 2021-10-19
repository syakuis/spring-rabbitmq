package io.github.syakuis.consumer.push.infrastucture.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 * https://www.baeldung.com/spring-amqp-error-handling
 */
@Slf4j
@Component
public class CustomRabbitListenerErrorHandler implements RabbitListenerErrorHandler {
    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        log.error("{}", message);

        throw new ImmediateAcknowledgeAmqpException("");
    }
}
