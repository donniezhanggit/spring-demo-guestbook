package gb.model;

import static lombok.AccessLevel.PRIVATE;

import javax.annotation.Nullable;

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
public final class UserFullNameChanged extends BaseDomainEvent {
    Long userId;
    FullName oldName;
    FullName newName;


    public static UserFullNameChanged
    of(@NonNull final User user, @Nullable final FullName newName) {
        return builder()
                .userId(user.getId())
                .oldName(user.getFullName().orElse(null))
                .newName(newName)
                .build();
    }
}
