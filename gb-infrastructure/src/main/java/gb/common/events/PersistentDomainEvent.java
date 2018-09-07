package gb.common.events;

import static gb.common.events.DomainEventStatus.*;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@FieldDefaults(level=PRIVATE)
@Builder
class PersistentDomainEvent {
    @Id
    @NonNull
    UUID id;

    @Version
    Short version;

    @NonNull
    String payload;

    @Builder.Default
    @Enumerated(STRING)
    DomainEventStatus status = PENDING;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime processedAt;


    void markProcessed() {
        markProcessed(LocalDateTime.now());
    }


    void markProcessed(@NonNull final LocalDateTime at) {
        status = PROCESSED;
        processedAt = at;
    }
}
