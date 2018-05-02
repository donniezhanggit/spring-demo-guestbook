package gb.test.it.api;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.repos.CommentsRepository;
import gb.test.dto.CommentInputBuilder;


@Service
public class CommentsFixtures {
    public static final Long NON_EXISTENT_ID = Long.MAX_VALUE;
    public static final String ANON_NAME = "anon";
    public static final String MESSAGE = "message";
    public static final LocalDateTime CREATED1 =
            LocalDateTime.of(2017, 9, 1, 12, 34, 16);
    public static final LocalDateTime CREATED2 =
            LocalDateTime.of(2017, 9, 1, 12, 33, 19);
    public static final LocalDateTime CREATED3 =
            LocalDateTime.of(2017, 9, 1, 12, 37, 31);


    @Autowired
    private CommentsRepository commentsRepo;


    public void savedCommentList() {
        this.commentsRepo.save(buildCommentsList());
    }


    public long existingCommentId() {
        final Comment comment = withNameAndMessage()
                        .created(CREATED1)
                        .build();

        return this.commentsRepo.save(comment).getId();
    }


    public Comment savedComment() {
        final CommentBuilder cb = withNameAndMessage();
        final Comment comment = cb.created(CREATED1).build();

        return this.commentsRepo.save(comment);
    }


    private static List<Comment> buildCommentsList() {
        final CommentBuilder cb = withNameAndMessage();
        final Comment comment1 = cb.created(CREATED1).build();
        final Comment comment2 = cb.created(CREATED2).build();
        final Comment comment3 = cb.created(CREATED3).build();

        return Arrays.asList(comment1, comment2, comment3);
    }


    private static CommentBuilder withNameAndMessage() {
        return new CommentBuilder().name(ANON_NAME).message(MESSAGE);
    }


    public static CommentInputBuilder commentInputBuilderWithNameAndMessage() {
        return new CommentInputBuilder()
                .name(ANON_NAME).message(MESSAGE);
    }
}
