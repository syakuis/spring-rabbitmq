package io.github.syakuis.producer.push.service;

import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMessageBody;
import io.github.syakuis.producer.push.infrastucture.amqp.AmqpMethod;
import io.github.syakuis.producer.push.model.PushMessagePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@Service
public class PushProducerService {
    @Resource(name = "pushRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public void send(PushMessagePayload pushMessagePayload) {
        rabbitTemplate.convertAndSend(AmqpMessageBody.builder()
                .method(AmqpMethod.CREATE)
                .payload(pushMessagePayload)
            .build());
    }
}
