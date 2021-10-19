package io.github.syakuis.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerServerApplication.class, args);
    }
}
