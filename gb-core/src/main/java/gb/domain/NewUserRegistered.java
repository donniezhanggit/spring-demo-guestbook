package gb.domain;

import static lombok.AccessLevel.PRIVATE;

import gb.common.events.BaseDomainEvent;
import gb.common.events.annotations.PersistentDomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@Builder
@FieldDefaults(level=PRIVATE, makeFinal=true)
@EqualsAndHashCode(callSuper=true)
@PersistentDomainEvent
public final class NewUserRegistered extends BaseDomainEvent {
    Long userId;
    @NonNull String userName;
    @NonNull String email;


    public static NewUserRegistered of(@NonNull final User newUser) {
        return builder()
                .userId(newUser.getId())
                .userName(newUser.getUserName())
                .email(newUser.getEmail())
                .build();
    }
}
