package gb.testlang.fixtures;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static gb.testlang.fixtures.UsersFixtures.buildUser;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gb.dto.CommentEntry;
import gb.dto.CommentEntryBuilder;
import gb.dto.CommentInput;
import gb.dto.CommentInputBuilder;
import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.model.User;
import gb.repos.CommentsRepository;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level=PRIVATE)
public class CommentsFixtures {
    public static final long EXISTING_ID = 12481L;
    public static final long NON_EXISTENT_ID = Long.MAX_VALUE;
    public static final short VERSION_JUST_CREATED = 0;
    public static final String ANON_NAME = "A test anon";
    public static final String MESSAGE = "A test message";
    public static final LocalDateTime CREATED1 =
            LocalDateTime.of(2017, 9, 1, 12, 34, 16);
    public static final LocalDateTime CREATED2 =
            LocalDateTime.of(2017, 9, 1, 12, 33, 19);
    public static final LocalDateTime CREATED3 =
            LocalDateTime.of(2017, 9, 1, 12, 37, 31);
    public static final String USERNAME_DIV_TEXT =
            "<div class=\"name\">" + EXISTING_USERNAME + "</div>\n" +
            "          <div class=\"anon\"></div>";
    public static final String ANON_NAME_DIV_TEXT =
            "<div class=\"name\"></div>\n" +
            "          <div class=\"anon\">" + ANON_NAME + "</div>";


    @Autowired
    CommentsRepository commentsRepo;


    public void savedCommentList() {
        commentsRepo.saveAll(buildCommentsList());
    }


    public long existingCommentId() {
        return existingComment().getId();
    }


    public Comment existingComment() {
        final Comment comment = commentWithAnonNameAndMessage()
                .createdAt(CREATED1).build();

        return commentsRepo.save(comment);
    }


    public static List<Comment> buildCommentsList() {
        final CommentBuilder cb = commentWithAnonNameAndMessage();
        final Comment comment1 = cb.createdAt(CREATED1).build();
        final Comment comment2 = cb.createdAt(CREATED2).build();
        final Comment comment3 = cb.createdAt(CREATED3).build();

        return Arrays.asList(comment2, comment1, comment3);
    }


    public static List<CommentEntry> buildCommentEntriesList() {
        final CommentEntry entry1 = buildAnonCommentEntry();
        final CommentEntry entry2 = buildUserCommentEntry();

        return Arrays.asList(entry1, entry2);
    }


    public static CommentBuilder commentWithAnonNameAndMessage() {
        return new CommentBuilder().anonName(ANON_NAME).message(MESSAGE);
    }


    public static CommentInput buildCommentInputWithMessage() {
        return new CommentInputBuilder().message(MESSAGE).build();
    }


    public static CommentInput buildAnonCommentInput() {
        return filledCommentInputBuilder().build();
    }


    public static CommentInputBuilder filledCommentInputBuilder() {
        return new CommentInputBuilder().anonName(ANON_NAME).message(MESSAGE);
    }


    public static CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .id(EXISTING_ID).version(VERSION_JUST_CREATED)
                .created(CREATED1).anonName(ANON_NAME).message(MESSAGE)
                .userId(null).userName(null).build();
    }


    public static CommentEntry buildUserCommentEntry() {
        return new CommentEntryBuilder()
                .anonName(null).created(CREATED1).message(MESSAGE)
                .userId(UsersFixtures.EXISTING_ID).userName(EXISTING_USERNAME)
                .build();
    }


    public static CommentBuilder filledCommentBuilder() {
        return new CommentBuilder()
                .createdAt(CREATED1).anonName(ANON_NAME).message(MESSAGE);
    }


    public static Comment buildAnonComment() {
        return filledCommentBuilder().user(Optional.empty()).build();
    }


    public static Comment buildUserComment() {
        return buildCommentFor(buildUser());
    }


    public static Comment buildCommentFor(@Nullable final User user) {
        return filledCommentBuilder().anonName(null).user(user).build();
    }
}
