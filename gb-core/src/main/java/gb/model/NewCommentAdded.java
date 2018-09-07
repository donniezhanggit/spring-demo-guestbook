package gb.model;

import java.time.LocalDateTime;
import java.util.UUID;

import gb.common.events.DomainEvent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
public class NewCommentAdded implements DomainEvent {
    UUID id = UUID.randomUUID();
    @NonNull @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
    @NonNull Long commentId;
    @NonNull String message;
    @NonNull String authorName;
}
