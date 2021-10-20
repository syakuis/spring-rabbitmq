package io.github.syakuis.consumer.push.infrastucture.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMessageBody;
import io.github.syakuis.producer.push.model.PushMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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

    @RabbitListener(queues = "queue.retry-push")
    public void invoker(final Message message) throws Exception {
        if (Objects.equals("ON", RetryPushResolver.status)) {
            throw new InterruptedException();
        } else {
            AmqpMessageBody<PushMessagePayload> amqpMessageBody = objectMapper.readValue(message.getBody(), AmqpMessageBody.class);
            log.debug("[{}] consumer: {}", message.getMessageProperties().getTimestamp(), amqpMessageBody);
        }
    }
}
