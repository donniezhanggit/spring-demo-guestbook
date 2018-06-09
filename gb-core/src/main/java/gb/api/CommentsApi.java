package gb.api;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import gb.dto.CommentEntry;
import gb.dto.CommentInput;


/**
 * Public front service of comments.
 *
 * @author whitesquall
 *
 */
public interface CommentsApi {
    /**
     * Returns a list of all comments ordered by date.
     *
     * @category query
     * @return List of mapped comment entries.
     */
    List<CommentEntry> getComments();


    /**
     * Get comment by ID.
     *
     * @category query
     * @param id A unique identifier of comment.
     * @return if comment with ID exists return filled optional with
     *         {@link CommentEntry}, otherwise {@code Optional.empty()}
     */
    Optional<CommentEntry> getComment(final long id);


    /**
     * Create a new comment by input.
     *
     * @category command
     * @param input {@link CommentInput} with comments data
     * @return ID of just created comment.
     * @throws ConstraintViolationException if input is invalid.
     */
    Long createComment(@Nonnull @Valid final CommentInput input);


    /**
     * Idempotent removing of comment by ID. If comment with ID does not exist
     * just return silently.
     *
     * @category command
     * @param id an identifier of comment to remove.
     */
    void removeComment(final long id);
}
