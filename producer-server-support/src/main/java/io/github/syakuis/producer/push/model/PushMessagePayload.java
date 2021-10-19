package io.github.syakuis.producer.push.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PushMessagePayload {
    String message;
    LocalDateTime createdOn;

    @Builder
    public PushMessagePayload(String message) {
        this.message = message;
        this.createdOn = LocalDateTime.now();
    }
}
