package gb.services;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.stereotype.Service;

import gb.domain.Comment;
import gb.domain.User;
import gb.dto.CommentInput;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentMapper {
    @NonNull CurrentPrincipalService currentPrincipalService;


    public Comment from(@NonNull final CommentInput input) {
        return Comment.builder()
            .anonName(input.getAnonName())
            .message(input.getMessage())
            .user(getCurrentUserIfPresent())
            .build();
    }


    private User getCurrentUserIfPresent() {
        return currentPrincipalService.getCurrentUser().orElse(null);
    }
}
