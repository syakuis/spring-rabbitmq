package io.github.syakuis.producer.push;

import io.github.syakuis.producer.push.model.PushMessagePayload;
import io.github.syakuis.producer.push.service.PushProducerService;
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
@RequestMapping("/push")
public class PushRestController {
    private final PushProducerService pushProducerService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void effect(@RequestBody MultiValueMap<String, Object> parameters) {
        pushProducerService.send(PushMessagePayload.builder()
                .message(parameters.toString())
            .build());
        log.trace("parameters: {}", parameters);
    }
}
