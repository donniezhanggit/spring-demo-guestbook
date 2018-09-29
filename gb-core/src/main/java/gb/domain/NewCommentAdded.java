package gb.domain;

import static lombok.AccessLevel.PRIVATE;

import gb.common.events.BaseDomainEvent;
import gb.common.events.annotations.PersistentDomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Value
@Builder
@FieldDefaults(level=PRIVATE, makeFinal=true)
@EqualsAndHashCode(callSuper=true)
@PersistentDomainEvent
public final class NewCommentAdded extends BaseDomainEvent {
    Long commentId;
    @NonNull String authorName;
    @NonNull String message;


    public static NewCommentAdded of(@NonNull final Comment newComment) {
        val authorName = newComment.getUser()
                .map(User::getUserName)
                .orElse(newComment.getAnonName());

        return builder()
                .commentId(newComment.getId())
                .authorName(authorName)
                .message(newComment.getMessage())
                .build();
    }
}
