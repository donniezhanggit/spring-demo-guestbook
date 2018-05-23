package gb.dto;


import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import gb.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;


@Getter
@Wither(value=PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE)
@FieldDefaults(level=PRIVATE)
public class CommentEntry {
    Long id;
    Short version;
    LocalDateTime created;
    String anonName;
    String message;


    SimpleUserEntry user;


    public static CommentEntry from(@NonNull final Comment comment) {
        SimpleUserEntry userEntry = comment.getUser()
                .map(SimpleUserEntry::from).orElse(null);

        return new CommentEntry()
            .withId(comment.getId())
            .withVersion(comment.getVersion())
            .withCreated(comment.getCreated())
            .withMessage(comment.getMessage())
            .withAnonName(comment.getName())
            .withUser(userEntry);
    }
}
