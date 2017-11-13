package gb.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        final List<Comment> comments = this.commentRepo
            .findAllByOrderByCreatedAsc();

        return comments.stream()
            .map(CommentEntry::from)
            .collect(Collectors.toList());
    }


    public Optional<CommentEntry> getComment(final long id) {
        final Optional<Comment> comment = this.commentRepo.findOne(id);

        return comment.map(CommentEntry::from);
    }


    @Transactional
    @CacheEvict(allEntries=true)
    public
    CommentEntry createComment(@Nonnull @Valid final CommentInput input) {
        final Comment comment = this.commentRepo.save(new Comment(input));

        return CommentEntry.from(comment);
    }
}
