package io.github.syakuis.producer.push.infrastucture.amqp;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-08-01
 */
@Builder
@RequiredArgsConstructor
@Getter
@ToString
public class AmqpMessageBody<T> {
    private final String method;
    private final T payload;
}
