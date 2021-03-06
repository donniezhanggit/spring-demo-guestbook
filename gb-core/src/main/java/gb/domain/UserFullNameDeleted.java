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
public final class UserFullNameDeleted extends BaseDomainEvent {
    Long userId;
    FullName oldName;


    public static UserFullNameDeleted of(@NonNull final User user) {
        return builder()
                .userId(user.getId())
                .oldName(user.getFullName().orElse(null))
                .build();
    }
}
