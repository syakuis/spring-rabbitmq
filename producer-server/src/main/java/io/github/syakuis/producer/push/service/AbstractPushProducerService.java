package io.github.syakuis.producer.push.service;

import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMessageBody;
import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMethod;
import io.github.syakuis.producer.push.model.PushMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.Date;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@RequiredArgsConstructor
public class AbstractPushProducerService {
    private final MessageConverter messageConverter;
    private final AmqpTemplate amqpTemplate;

    public void send(PushMessagePayload pushMessagePayload) {
        MessageProperties headers = new MessageProperties();
        headers.setTimestamp(new Date());

        amqpTemplate.convertAndSend(messageConverter.toMessage(AmqpMessageBody.builder()
            .method(AmqpMethod.CREATE)
            .payload(pushMessagePayload)
            .build(), headers));
    }
}
