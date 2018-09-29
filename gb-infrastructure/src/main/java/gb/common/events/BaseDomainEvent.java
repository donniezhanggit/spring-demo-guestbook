package gb.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NonNull;


public abstract class BaseDomainEvent
implements DomainEvent {
    @NonNull UUID id = UUID.randomUUID();
    @NonNull LocalDateTime createdAt = LocalDateTime.now();


    @Override
    public final UUID getId() {
        return id;
    }


    @Override
    public final LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
