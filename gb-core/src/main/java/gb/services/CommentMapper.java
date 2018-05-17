package gb.services;

import static lombok.AccessLevel.PRIVATE;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentMapper {
    CurrentPrincipalService currentPrincipalService;


    public CommentMapper(
        @Nonnull final CurrentPrincipalService currentPrincipalService) {
        this.currentPrincipalService = currentPrincipalService;
    }


    public Comment from(@Nonnull final CommentInput input) {
        return new CommentBuilder()
            .name(input.getName())
            .message(input.getMessage())
            .user(currentPrincipalService.getCurrentUser())
            .build();
    }
}
