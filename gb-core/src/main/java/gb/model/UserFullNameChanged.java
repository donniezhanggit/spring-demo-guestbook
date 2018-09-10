package gb.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.annotation.Nullable;

import gb.common.events.DomainEvent;
import gb.common.events.annotations.PersistentDomainEvent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
@PersistentDomainEvent
public final class UserFullNameChanged implements DomainEvent {
    @NonNull UUID id = UUID.randomUUID();
    @NonNull @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
    Long userId;
    FullName oldName;
    FullName newName;


    public static UserFullNameChanged
    of(@NonNull final User user, @Nullable final FullName newName) {
        return UserFullNameChanged.builder()
                .userId(user.getId())
                .oldName(user.getFullName().orElse(null))
                .newName(newName)
                .build();
    }
}
