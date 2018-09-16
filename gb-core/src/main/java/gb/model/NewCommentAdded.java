package gb.model;

import gb.common.events.BaseDomainEvent;
import gb.common.events.annotations.PersistentDomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.val;


@Value
@Builder
@PersistentDomainEvent
@EqualsAndHashCode(callSuper=true)
public final class NewCommentAdded extends BaseDomainEvent {
    Long commentId;
    @NonNull String message;
    @NonNull String authorName;


    public static NewCommentAdded of(@NonNull final Comment newComment) {
        val authorName = newComment.getUser()
                .map(User::getUserName)
                .orElse(newComment.getAnonName());

        return builder()
                .authorName(authorName)
                .commentId(newComment.getId())
                .message(newComment.getMessage())
                .build();
    }
}
