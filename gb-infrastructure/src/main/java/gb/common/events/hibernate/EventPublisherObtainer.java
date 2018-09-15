package gb.common.events.hibernate;

import org.springframework.stereotype.Component;

import gb.common.events.EventPublisher;
import lombok.NonNull;


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
