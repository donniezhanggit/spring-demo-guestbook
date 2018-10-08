package gb.dto;


import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.time.LocalDateTime;

import gb.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
public class CommentEntry implements Serializable {
    private static final long serialVersionUID = -7280241741763657458L;

    Long id;
    Short version;
    LocalDateTime createdAt;
    String anonName;
    String message;
    SimpleUserEntry user;


    public static CommentEntry from(@NonNull final Comment comment) {
        SimpleUserEntry userEntry = comment.getUser()
                .map(SimpleUserEntry::from).orElse(null);

        return CommentEntry.builder()
            .id(comment.getId())
            .version(comment.getVersion())
            .createdAt(comment.getCreatedAt())
            .message(comment.getMessage())
            .anonName(comment.getAnonName())
            .user(userEntry)
            .build();
    }
}
