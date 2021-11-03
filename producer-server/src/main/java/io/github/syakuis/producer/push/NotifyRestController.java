package io.github.syakuis.producer.push;

import io.github.syakuis.producer.push.model.NotifyMessagePayload;
import io.github.syakuis.producer.push.model.NotifyMessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@RestController
@RequestMapping("/notify")
public class NotifyRestController {
    @Resource(name = "jsonMessageConverter")
    private MessageConverter messageConverter;

    @Resource(name = "notifyRabbitTemplate")
    private AmqpTemplate amqpTemplate;

    @PostMapping("/{condition}")
    @ResponseStatus(HttpStatus.OK)
    public void message(@PathVariable("condition") String condition) {
        MessageProperties headers = new MessageProperties();
        headers.setTimestamp(new Date());

        NotifyMessagePayload payload;

        if (Objects.equals("normal", condition)) {
            payload = NotifyMessagePayload.builder().message("정상 메시지").count(0L).build();
        } else {
            payload = NotifyMessagePayload.builder().message("문제가 있는 메시지").count(10L).build();
        }
        amqpTemplate.convertAndSend(NotifyMessageProperties.QUEUE_NAME,
            messageConverter.toMessage(payload, headers));
        log.debug("[push-{}] {}", headers.getTimestamp(), payload);

    }
}
