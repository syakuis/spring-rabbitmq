package io.github.syakuis.producer.push.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@Service
public class RetryPushProducerService extends AbstractPushProducerService {
    @Autowired
    public RetryPushProducerService(@Qualifier("retryPushRabbitTemplate") RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }
}
