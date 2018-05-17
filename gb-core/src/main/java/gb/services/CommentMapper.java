package gb.services;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;

import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.model.User;
import lombok.val;
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
        final Optional<User> currentUser = currentPrincipalService
                .getCurrentUser();
        val newComment = new CommentBuilder()
        		.name(input.getName())
        		.message(input.getMessage())
        		.build();

        currentUser.ifPresent(u -> {
        	newComment.setUser(u);
        	newComment.setName(null);
        });

        return newComment;
    }
}
