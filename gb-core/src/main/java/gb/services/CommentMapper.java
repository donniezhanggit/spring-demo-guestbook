package gb.services;

import static lombok.AccessLevel.PRIVATE;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentMapper {
    @NonNull CurrentPrincipalService currentPrincipalService;


    public Comment from(@Nonnull final CommentInput input) {
        return new CommentBuilder()
            .anonName(input.getAnonName())
            .message(input.getMessage())
            .user(currentPrincipalService.getCurrentUser())
            .build();
    }
}
