package io.github.syakuis.consumer.push.infrastucture.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.syakuis.producer.push.model.NotifyMessagePayload;
import io.github.syakuis.producer.push.model.NotifyMessageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-08-19
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DeadNotifyMessageConsumer {
    private final ObjectMapper objectMapper;
    private final Map<String, Long> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        store.put("count", 0L);
    }

    public synchronized void setCount(Long count) {
        store.put("count", count);
    }

    @RabbitListener(queues = NotifyMessageProperties.DEAD_LETTER_QUEUE_NAME, ackMode = "MANUAL"
//        , containerFactory = "deadListenerContainer"
    )
    public void invoker(final Message message) throws IOException {
        NotifyMessagePayload amqpMessageBody = objectMapper.readValue(message.getBody(), NotifyMessagePayload.class);

        if (amqpMessageBody.getCount() > store.get("count")) {
            log.error("[notify-{}] consumer: {}", message.getMessageProperties().getTimestamp(), amqpMessageBody);
            throw new IllegalArgumentException("오류 발생");
        }

        log.debug("[notify-{}] consumer: {}", message.getMessageProperties().getTimestamp(), amqpMessageBody);
    }
}
