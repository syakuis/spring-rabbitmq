package io.github.syakuis.consumer.push;

import io.github.syakuis.consumer.push.infrastucture.amqp.DeadNotifyMessageConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/dead-notify")
public class DeadNotifyRestController {
    private final DeadNotifyMessageConsumer deadNotifyMessageConsumer;

    @PostMapping("/{count}")
    @ResponseStatus(HttpStatus.OK)
    public void message(@PathVariable("count") Long count) {
        deadNotifyMessageConsumer.setCount(count);
    }
}
