package gb.common.events;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Interface of domain event payload.
 *
 * Also provides default type information implementation, which needed
 * for polymorphic event serialization/deserialization by Jackson.
 */
public interface DomainEvent {
    UUID getId();
    LocalDateTime getCreatedAt();


    default String getType() {
        return getClass().getSimpleName();
    }
}
