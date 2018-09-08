package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
class EventPublisherImpl implements EventPublisher {
    @NonNull ApplicationEventPublisher publisher;


    @Override
    public void publishEvent(@NonNull final DomainEvent event) {
        log.info("Generating new event: {}", event);

        publisher.publishEvent(event);
    }
}
