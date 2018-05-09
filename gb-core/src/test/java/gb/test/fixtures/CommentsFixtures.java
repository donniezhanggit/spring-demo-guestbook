package gb.test.fixtures;

import static gb.test.fixtures.UsersFixtures.buildUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.repos.CommentsRepository;
import gb.test.dto.CommentEntryBuilder;
import gb.test.dto.CommentInputBuilder;


@Service
public class CommentsFixtures {
    public static final long EXISTING_ID = 1L;
    public static final long NON_EXISTENT_ID = Long.MAX_VALUE;
    public static final short VERSION_JUST_CREATED = 0;
    public static final String ANON_NAME = "A test anon";
    public static final String MESSAGE = "A test message";
    public static final String USERNAME = "user";
    public static final LocalDateTime CREATED1 =
            LocalDateTime.of(2017, 9, 1, 12, 34, 16);
    public static final LocalDateTime CREATED2 =
            LocalDateTime.of(2017, 9, 1, 12, 33, 19);
    public static final LocalDateTime CREATED3 =
            LocalDateTime.of(2017, 9, 1, 12, 37, 31);


    @Autowired
    private CommentsRepository commentsRepo;


    public void savedCommentList() {
        commentsRepo.saveAll(buildCommentsList());
    }


    public long existingCommentId() {
        return existingComment().getId();
    }


    public Comment existingComment() {
        final Comment comment = commentWithNameAndMessage()
                        .created(CREATED1)
                        .build();

        return commentsRepo.save(comment);
    }


    public Comment savedComment() {
        final CommentBuilder cb = commentWithNameAndMessage();
        final Comment comment = cb.created(CREATED1).build();

        return commentsRepo.save(comment);
    }


    public static List<Comment> buildCommentsList() {
        final CommentBuilder cb = commentWithNameAndMessage();
        final Comment comment1 = cb.created(CREATED1).build();
        final Comment comment2 = cb.created(CREATED2).build();
        final Comment comment3 = cb.created(CREATED3).build();

        return Arrays.asList(comment1, comment2, comment3);
    }


    public static CommentBuilder commentWithNameAndMessage() {
        return new CommentBuilder().name(ANON_NAME).message(MESSAGE);
    }


    public static CommentInput buildAnonCommentInput() {
        return commentInputBuilderWithNameAndMessage().build();
    }


    public static CommentInputBuilder commentInputBuilderWithNameAndMessage() {
        return new CommentInputBuilder()
                .name(ANON_NAME).message(MESSAGE);
    }


    public static CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .id(EXISTING_ID).version(VERSION_JUST_CREATED)
                .created(CREATED1).anonName(ANON_NAME).message(MESSAGE)
                .username(null).build();
    }


    public static CommentEntry buildUserCommentEntry() {
        return new CommentEntryBuilder()
                .anonName(null).created(CREATED1).message(MESSAGE)
                .username(USERNAME).build();
    }

    public static CommentBuilder getCommentBuilder() {
        return new CommentBuilder()
                .created(CREATED1).name(ANON_NAME).message(MESSAGE);
    }


    public static Comment buildAnonComment() {
        return getCommentBuilder().user(null).build();
    }


    public static Comment buildUserComment() {
        return getCommentBuilder()
                .name(null)
                .user(buildUser())
                .build();
    }
}
