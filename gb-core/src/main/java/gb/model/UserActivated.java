package gb.model;

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
public final class UserActivated extends BaseDomainEvent {
    Long userId;


    public static UserActivated of(@NonNull final User user) { // NOSONAR
        return builder()
                .userId(user.getId())
                .build();
    }
}
