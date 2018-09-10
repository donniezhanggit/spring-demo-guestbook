package gb.model;

import java.time.LocalDateTime;
import java.util.UUID;

import gb.common.events.DomainEvent;
import gb.common.events.annotations.PersistentDomainEvent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
@PersistentDomainEvent
public final class NewCommentAdded implements DomainEvent {
    @NonNull UUID id = UUID.randomUUID();
    @NonNull @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
    @NonNull Long commentId;
    @NonNull String message;
    @NonNull String authorName;
}
