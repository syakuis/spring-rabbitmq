package io.github.syakuis.producer.push;

import io.github.syakuis.producer.push.model.PushMessagePayload;
import io.github.syakuis.producer.push.service.RetryPushProducerService;
import io.github.syakuis.producer.push.support.PushSchedulerSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/retry-push")
public class RetryPushRestController {
    private final PushSchedulerSupport pushSchedulerSupport;
    private final RetryPushProducerService retryPushProducerService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void effect(@PathVariable("id") String id, @RequestBody MultiValueMap<String, Object> parameters) {
        log.debug("{} : Start", id);
        Runnable runnable = () -> retryPushProducerService.send(PushMessagePayload.builder()
            .message(parameters.toString())
            .build());

        pushSchedulerSupport.register(id, runnable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void stop(@PathVariable("id") String id) {
        log.debug("{} : Stop", id);
        pushSchedulerSupport.remove(id);
    }
}
