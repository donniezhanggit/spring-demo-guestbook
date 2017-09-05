package gb.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import gb.common.annotations.Api;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.repos.CommentsRepository;


@Api
@Transactional(readOnly=true)
public class CommentsApi {
    private final CommentsRepository commentRepo;


    public CommentsApi(final CommentsRepository commentRepo) {
        this.commentRepo = commentRepo;
    }


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
    public
    CommentEntry createComment(@Valid final CommentInput input) {
        final Comment comment = this.commentRepo.save(new Comment(input));

        return CommentEntry.from(comment);
    }
}
