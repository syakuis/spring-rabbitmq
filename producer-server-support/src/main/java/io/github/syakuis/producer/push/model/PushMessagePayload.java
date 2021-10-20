package io.github.syakuis.producer.push.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PushMessagePayload {
    String message;
}
