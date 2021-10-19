package io.github.syakuis.consumer.push.infrastucture.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMessageBody;
import io.github.syakuis.producer.push.model.PushMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RetryPushMessageConsumer {
    private final ObjectMapper objectMapper;

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "queue.consumer-server.retry-push"),
        exchange = @Exchange(value = "exchange.producer-server.retry-push", type = ExchangeTypes.FANOUT)
    ), errorHandler = "customRabbitListenerErrorHandler")
    public void invoker(final Message message) throws Exception {
        if (Objects.equals("ON", RetryPushResolver.status)) {
            throw new InterruptedException();
        } else {
            AmqpMessageBody<PushMessagePayload> amqpMessageBody = objectMapper.readValue(message.getBody(), AmqpMessageBody.class);
            log.debug("consumer: {}", amqpMessageBody);
        }
    }
}
