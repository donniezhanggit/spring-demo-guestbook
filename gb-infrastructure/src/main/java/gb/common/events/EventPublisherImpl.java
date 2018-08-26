package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
class EventPublisherImpl implements EventPublisher {
    @NonNull ApplicationEventPublisher publisher;


    @Override
    public void raise(@NonNull final Object event) {
        publisher.publishEvent(event);
    }
}
