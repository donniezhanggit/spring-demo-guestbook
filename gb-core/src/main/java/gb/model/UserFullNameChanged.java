package gb.model;

import javax.annotation.Nullable;

import gb.common.events.BaseDomainEvent;
import gb.common.events.annotations.PersistentDomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
@PersistentDomainEvent
@EqualsAndHashCode(callSuper=true)
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
