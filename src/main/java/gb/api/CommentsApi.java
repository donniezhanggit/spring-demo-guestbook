package gb.api;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import gb.common.annotations.Api;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.repos.CommentsRepository;


@Api
@Transactional(readOnly=true)
@CacheConfig(cacheNames="comments")
public class CommentsApi {
    private final CommentsRepository commentRepo;


    public CommentsApi(@Nonnull final CommentsRepository commentRepo) {
        this.commentRepo = commentRepo;
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

        System.out.println(entry);

        return entry;
    }


    @Transactional
    @CacheEvict(allEntries=true)
    public
    Long createComment(@Nonnull @Valid final CommentInput input) {
        final Comment comment = this.commentRepo.save(new Comment(input));

        return comment.getId();
    }
}
