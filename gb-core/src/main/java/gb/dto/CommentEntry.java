package gb.dto;


import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import gb.model.Comment;
import gb.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;


@Getter
@Wither
@NoArgsConstructor(access=PRIVATE)
@FieldDefaults(level=PRIVATE)
@AllArgsConstructor
public class CommentEntry {
    Long id;
    Short version;
    LocalDateTime created;
    String anonName;
    String message;
    String username;


    public static CommentEntry from(@Nonnull final Comment comment) {
        final String username = comment.getUser()
                .map(User::getUsername).orElse(null);

        return new CommentEntry()
                .withId(comment.getId())
                .withVersion(comment.getVersion())
                .withCreated(comment.getCreated())
                .withMessage(comment.getMessage())
                .withAnonName(comment.getName())
                .withUsername(username);
    }
}
