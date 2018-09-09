package gb.model;

import java.time.LocalDateTime;
import java.util.UUID;

import gb.common.events.DomainEvent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
public final class UserFullNameDeleted implements DomainEvent {
    @NonNull UUID id = UUID.randomUUID();
    @NonNull @Builder.Default LocalDateTime createdAt = LocalDateTime.now();
    Long userId;
    FullName oldName;


    public static UserFullNameDeleted of(@NonNull final User user) {
        return UserFullNameDeleted.builder()
                .userId(user.getId())
                .oldName(user.getFullName().orElse(null))
                .build();
    }
}
