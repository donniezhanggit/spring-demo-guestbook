package gb.common.events;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@Async
class EventLogger implements EventDelegator {
    @Override
    public void delegate(final Object event) {
        log.info("Thread name: {}", Thread.currentThread().getName());
        log.info("All events after commit: {}", event);
    }
}
