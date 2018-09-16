package gb.model;

import static lombok.AccessLevel.PRIVATE;

import gb.common.events.BaseDomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@Builder
@FieldDefaults(level=PRIVATE, makeFinal=true)
@EqualsAndHashCode(callSuper=true)
public final class UserDeactivated extends BaseDomainEvent {
    Long userId;


    public static UserDeactivated of(@NonNull final User user) { // NOSONAR
        return builder()
                .userId(user.getId())
                .build();
    }
}
