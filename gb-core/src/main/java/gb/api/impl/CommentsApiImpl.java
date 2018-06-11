package gb.api.impl;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;

import gb.api.CommentsApi;
import gb.common.annotations.Api;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.repos.CommentsRepository;
import gb.services.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Api
@Transactional(readOnly=true)
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentsApiImpl
implements CommentsApi {
    @NonNull CommentsRepository commentsRepo;
    @NonNull CommentMapper commentMapper;


    @Override
    public List<CommentEntry> getComments() {
        final List<CommentEntry> comments = commentsRepo
            .findAllByOrderByCreatedAsc(CommentEntry.class);

        return comments;
    }


    @Override
    public Optional<CommentEntry> getComment(final long id) {
        final Optional<CommentEntry> entry = commentsRepo
                .findOneById(id, CommentEntry.class);

        return entry;
    }


    @Override
    @Transactional
    public Long createComment(@Nonnull @Valid final CommentInput input) {
        final Comment comment = commentsRepo.save(commentMapper.from(input));

        return comment.getId();
    }


    @Override
    @Transactional
    public void removeComment(final long id) {
        final Optional<Comment> comment = commentsRepo.findOneById(id);

        comment.ifPresent(commentsRepo::delete);
    }
}
