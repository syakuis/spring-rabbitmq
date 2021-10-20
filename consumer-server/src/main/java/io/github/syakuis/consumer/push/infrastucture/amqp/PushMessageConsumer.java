package io.github.syakuis.consumer.push.infrastucture.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMessageBody;
import io.github.syakuis.producer.push.model.PushMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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

    @RabbitListener(queues = "queue.push")
    public void invoker(final Message message) throws IOException {
        AmqpMessageBody<PushMessagePayload> amqpMessageBody = objectMapper.readValue(message.getBody(), AmqpMessageBody.class);

        log.debug("[{}] consumer: {}", message.getMessageProperties().getTimestamp(), amqpMessageBody);
    }
}
