package gb.api;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import gb.common.exceptions.NotFoundException;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;


/**
 * Public front service of comments.
 *
 * @author whitesquall
 *
 */
@Validated
@CacheConfig(cacheNames="comments")
public interface CommentsApi {
    /**
     * Returns a list of all comments ordered by date.
     *
     * @category query
     * @return List of mapped comment entries.
     */
    @Cacheable
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
    @PreAuthorize("hasRole('USER')")
    @CacheEvict(allEntries=true)
    Long createComment(@Nonnull @Valid final CommentInput input);


    /**
     * Idempotent removing of comment by ID.
     *
     * @category command
     * @param id an identifier of comment to remove.
     * @throws NotFoundException if comment by id not found
     */
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(allEntries=true)
    void removeComment(final long id);
}
