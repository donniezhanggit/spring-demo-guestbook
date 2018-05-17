package gb.api;

import static lombok.AccessLevel.PRIVATE;

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
import gb.repos.CommentsRepository;
import gb.services.CommentMapper;
import lombok.experimental.FieldDefaults;


@Api
@Transactional(readOnly=true)
@CacheConfig(cacheNames="comments")
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentsApi {
    CommentsRepository commentRepo;
    CommentMapper commentMapper;


    public CommentsApi(@Nonnull final CommentsRepository commentRepo,
            @Nonnull final CommentMapper commentMapper) {
        this.commentRepo = commentRepo;
        this.commentMapper = commentMapper;
    }


    @Cacheable
    public List<CommentEntry> getComments() {
        final List<CommentEntry> comments = commentRepo
            .findAllByOrderByCreatedAsc(CommentEntry.class);

        return comments;
    }


    public Optional<CommentEntry> getComment(final long id) {
        final Optional<CommentEntry> entry = commentRepo
                .findOneById(id, CommentEntry.class);

        return entry;
    }


    @Transactional
    @CacheEvict(allEntries=true)
    @PreAuthorize("hasRole('USER')")
    public Long createComment(@Nonnull @Valid final CommentInput input) {
        final Comment comment = commentRepo.save(commentMapper.from(input));

        return comment.getId();
    }


    @Transactional
    @CacheEvict(allEntries=true)
    @PreAuthorize("hasRole('ADMIN')")
    public void removeComment(long id) {
        final Optional<Comment> comment = commentRepo.findOneById(id);

        comment.ifPresent(commentRepo::delete);
    }
}
