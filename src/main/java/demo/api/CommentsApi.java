package demo.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;
import demo.common.annotations.Api;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
import demo.model.Comment;
import demo.repos.CommentRepository;


@Api
@Transactional(readOnly=true)
public class CommentsApi {
    private final CommentRepository commentRepo;


    public CommentsApi(@NotNull final CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }


    @NotNull
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


    @NotNull
    @Transactional
    public CommentEntry createComment(
        @NotNull @Valid final CommentInput input) {
        final Comment comment = this.commentRepo.save(new Comment(input));

        return CommentEntry.from(comment);
    }
}
