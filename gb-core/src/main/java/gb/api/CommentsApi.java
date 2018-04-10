package gb.api;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import gb.common.annotations.Api;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.User;
import gb.repos.CommentsRepository;
import gb.services.CurrentPrincipalService;


@Api
@Transactional(readOnly=true)
@CacheConfig(cacheNames="comments")
public class CommentsApi {
    private final CommentsRepository commentRepo;
    private final CurrentPrincipalService currentPrincipalService;


    public CommentsApi(@Nonnull final CommentsRepository commentRepo,
            @Nonnull final CurrentPrincipalService currentPrincipalService) {
        this.commentRepo = commentRepo;
        this.currentPrincipalService = currentPrincipalService;
    }


    @Cacheable
    public List<CommentEntry> getComments() {
        final List<CommentEntry> comments = this.commentRepo
            .findAllByOrderByCreatedAsc(CommentEntry.class);

        return comments;
    }


    public Optional<CommentEntry> getComment(final long id) {
        final Optional<CommentEntry> entry = this.commentRepo
                .findOneById(id, CommentEntry.class);

        return entry;
    }


    @Transactional
    @CacheEvict(allEntries=true)
    @PreAuthorize("hasRole('USER')")
    public Long createComment(@Nonnull @Valid final CommentInput input) {
        final Comment comment = this.commentRepo.save(
                this.buildCommentFromInput(input));

        return comment.getId();
    }


    @Transactional
    @CacheEvict(allEntries=true)
    @PreAuthorize("hasRole('ADMIN')")
    public void removeComment(long id) {
        final Optional<Comment> comment = this.commentRepo.findOne(id);

        comment.ifPresent(c -> this.commentRepo.delete(c));
    }


    private Comment buildCommentFromInput(@Nonnull final CommentInput input) {
        final Optional<User> currentUser = this.getUser();

        final Comment comment = new Comment(input);

        currentUser.ifPresent(user -> {
            comment.setName(null);
            comment.setUser(user);
        });

        return comment;
    }


    private Optional<User> getUser() {
        return this.currentPrincipalService.getCurrentUser();
    }
}
