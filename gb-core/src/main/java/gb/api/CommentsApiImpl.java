package gb.api;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;

import gb.common.annotations.Api;
import gb.common.events.EventPublisher;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.NewCommentAdded;
import gb.model.User;
import gb.repos.CommentsRepository;
import gb.services.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Api
@Transactional(readOnly=true)
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
class CommentsApiImpl
implements CommentsApi {
    @NonNull CommentsRepository commentsRepo;
    @NonNull CommentMapper commentMapper;
    @NonNull EventPublisher eventPublisher;


    @Override
    public List<CommentEntry> getComments() {
        return commentsRepo.findAllByOrderByCreatedAtAsc(CommentEntry.class);
    }


    @Override
    public Optional<CommentEntry> getComment(final long id) {
        return commentsRepo.findOneById(id, CommentEntry.class);
    }


    @Override
    @Transactional
    public Long createComment(@NonNull @Valid final CommentInput input) {
        final Comment comment = commentsRepo.save(commentMapper.from(input));
        final NewCommentAdded event = buildEvent(comment);

        eventPublisher.raise(event);

        return comment.getId();
    }


    @Override
    @Transactional
    public void removeComment(final long id) {
        final Comment comment = commentsRepo.findOneByIdOrThrow(id);

        commentsRepo.delete(comment);
    }


    private static NewCommentAdded buildEvent(final Comment newComment) {
        val authorName = newComment.getUser()
                .map(User::getUserName)
                .orElse(newComment.getAnonName());

        return NewCommentAdded.builder()
                .authorName(authorName)
                .commentId(newComment.getId())
                .message(newComment.getMessage())
                .build();
    }
}