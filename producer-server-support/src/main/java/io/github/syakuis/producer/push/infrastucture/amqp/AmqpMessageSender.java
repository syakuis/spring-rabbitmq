package io.github.syakuis.producer.push.infrastucture.amqp;

import java.lang.annotation.*;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-05-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AmqpMessageSender {
    AmqpMethod method() default AmqpMethod.CREATE;
}
