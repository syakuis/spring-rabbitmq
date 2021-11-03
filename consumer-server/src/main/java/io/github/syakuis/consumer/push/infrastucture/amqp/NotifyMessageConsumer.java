package io.github.syakuis.consumer.push.infrastucture.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.syakuis.producer.push.model.NotifyMessagePayload;
import io.github.syakuis.producer.push.model.NotifyMessageProperties;
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
public class NotifyMessageConsumer {
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = NotifyMessageProperties.QUEUE_NAME
//        , containerFactory = "listenerContainer"
    )
    public void invoker(final Message message) throws IOException {
        NotifyMessagePayload amqpMessageBody = objectMapper.readValue(message.getBody(), NotifyMessagePayload.class);

        if (amqpMessageBody.getCount() > 1) {
            log.error("[notify-{}] consumer: {}", message.getMessageProperties().getTimestamp(), amqpMessageBody);
            throw new IllegalArgumentException("오류 발생");
        }

        log.debug("[notify-{}] consumer: {}", message.getMessageProperties().getTimestamp(), amqpMessageBody);
    }
}