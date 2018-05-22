package gb.dto;


import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.function.Function;

import gb.model.Comment;
import gb.model.User;
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

    Long userId;

    String userName;

    public static CommentEntry from(@NonNull final Comment comment) {
        final Long userId = getUserFieldBy(comment, User::getId);
        final String userName = getUserFieldBy(comment, User::getUsername);

        return new CommentEntry()
            .withId(comment.getId())
            .withVersion(comment.getVersion())
            .withCreated(comment.getCreated())
            .withMessage(comment.getMessage())
            .withAnonName(comment.getName())
            .withUserId(userId)
            .withUserName(userName);
    }


    private static <T> T getUserFieldBy(
            @NonNull final Comment comment,
            @NonNull final Function<User, T> userMapper) {
        return comment.getUser().map(userMapper).orElse(null);
    }
}
