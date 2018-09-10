package gb.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

import gb.common.events.annotations.PersistentDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
@PersistentDomainEvent
@NoArgsConstructor(force=true)
@AllArgsConstructor
public class TestEvent implements DomainEvent {
    @NonNull @Builder.Default String type = "TestEvent";
    @NonNull UUID id = UUID.randomUUID();
    @NonNull @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
    @NonNull Long commentId;
}
