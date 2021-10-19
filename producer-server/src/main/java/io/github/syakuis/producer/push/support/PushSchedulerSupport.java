package io.github.syakuis.producer.push.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-10-19
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PushSchedulerSupport {
    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    public void register(final String id, final Runnable runnable) {
        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(runnable, 1000);
        tasks.put(id, task);
    }

    public void remove(final String id) {
        if (!tasks.containsKey(id)) {
            throw new IllegalArgumentException("id가 존재하지 않는 다.");
        }

        tasks.get(id).cancel(true);
    }

}
