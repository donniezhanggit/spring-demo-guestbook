package gb.dto;


import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import gb.model.Comment;
import lombok.AccessLevel;
import lombok.Data;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CommentEntry {
    Long id;
    Short version;
    LocalDateTime created;
    String anonName;
    String message;
    String username;


    public static CommentEntry from(@Nonnull final Comment comment) {
        val entry = new CommentEntry();

        entry.setId(comment.getId());
        entry.setVersion(comment.getVersion());
        entry.setCreated(comment.getCreated());
        entry.setMessage(comment.getMessage());
        entry.setAnonName(comment.getName());

        comment.getUser().ifPresent(user ->
            entry.setUsername(user.getUsername())
        );

        return entry;
    }
}
