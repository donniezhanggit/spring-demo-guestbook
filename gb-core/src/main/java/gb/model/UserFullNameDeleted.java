package gb.model;

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
public final class UserFullNameDeleted  extends BaseDomainEvent {
    Long userId;
    FullName oldName;


    public static UserFullNameDeleted of(@NonNull final User user) {
        return UserFullNameDeleted.builder()
                .userId(user.getId())
                .oldName(user.getFullName().orElse(null))
                .build();
    }
}
