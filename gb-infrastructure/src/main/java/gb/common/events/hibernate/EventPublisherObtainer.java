package gb.common.events.hibernate;

import org.springframework.stereotype.Component;

import gb.common.events.EventPublisher;
import lombok.NonNull;


/**
 * Helper for getting of EventPublisher in a static way.
 *
 * Workaround for {@link AggregateRootInterceptor}.
 *
 */
@Component
class EventPublisherObtainer {
    private static EventPublisher publisher;


    EventPublisherObtainer(@NonNull final EventPublisher p) {
        setPublisher(p);
    }


    public static EventPublisher obtain() {
        return publisher;
    }


    private static void
    setPublisher(@NonNull final EventPublisher publisher) {
        EventPublisherObtainer.publisher = publisher;
    }
}
