package io.github.syakuis.consumer.push;

import io.github.syakuis.consumer.push.infrastucture.amqp.RetryPushResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@RestController
@RequestMapping("/retry/push")
public class RetryPushRestController {
    @PutMapping("/{status}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("status") String status) {
        RetryPushResolver.status = status;
    }
}
