package io.github.syakuis.producer.push.model;

import lombok.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Builder
public class NotifyMessagePayload {
    String message;
    Long count;
}
