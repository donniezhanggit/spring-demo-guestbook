package gb.dto;


import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Nonnull;

import gb.model.Comment;
import gb.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
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
        val entry = new CommentEntry()
                .withId(comment.getId())
                .withVersion(comment.getVersion())
                .withCreated(comment.getCreated())
                .withMessage(comment.getMessage())
                .withAnonName(comment.getName())
                .withUsernameOf(comment.getUser());

        return entry;
    }


    public CommentEntry withUsernameOf(Optional<User> user) {
        final String username = user.map(User::getUsername).orElse(null);

        return new CommentEntry(id, version, created, anonName,
                message, username);
    }
}
