package gb.dto;


import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import gb.model.Comment;
import lombok.Data;
import lombok.val;


@Data
public class CommentEntry {
    private Long id;
    private Short version;
    private LocalDateTime created;
    private String anonName;
    private String message;
    private String username;


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
