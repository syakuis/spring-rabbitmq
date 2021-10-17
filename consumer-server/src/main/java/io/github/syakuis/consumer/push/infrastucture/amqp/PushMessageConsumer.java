package io.github.syakuis.consumer.push.infrastucture.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-08-19
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PushMessageConsumer {
    private final ObjectMapper objectMapper;

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "${app.rabbitmq.push-queue-name}", durable = "true"),
        exchange = @Exchange(value = "${app.rabbitmq.push-exchange-name}", type = ExchangeTypes.TOPIC),
        key = "*"
    ))
    public void invoker(final Message message) throws IOException {
    }

}
