package gb.common.events;

import java.time.LocalDateTime;
import java.util.UUID;


public interface DomainEvent {
    UUID getId();
    LocalDateTime getCreatedAt();


    default String getType() {
        return getClass().getSimpleName();
    }
}
