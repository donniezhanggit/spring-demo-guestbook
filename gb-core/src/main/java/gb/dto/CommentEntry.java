package gb.dto;


import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import gb.model.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;


@Value
@Wither(value=PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
public class CommentEntry {
    Long id;
    Short version;
    LocalDateTime createdAt;
    String anonName;
    String message;
    SimpleUserEntry user;


    public static CommentEntry from(@NonNull final Comment comment) {
        SimpleUserEntry userEntry = comment.getUser()
                .map(SimpleUserEntry::from).orElse(null);

        return new CommentEntry()
            .withId(comment.getId())
            .withVersion(comment.getVersion())
            .withCreatedAt(comment.getCreatedAt())
            .withMessage(comment.getMessage())
            .withAnonName(comment.getAnonName())
            .withUser(userEntry);
    }
}
