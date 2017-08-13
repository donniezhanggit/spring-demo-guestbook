package demo.test.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.Test;

import demo.dto.CommentEntry;
import demo.model.Comment;
import demo.model.CommentBuilder;
import demo.model.User;
import demo.model.UserBuilder;
import demo.test.common.JUnitTestCase;


public class CommentEntryTests extends JUnitTestCase {
    private static final String ANON_NAME = "A test anon";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "P4ssW0rD";
    private static final String EMAIL = "user@mail.org";
    private static final String MESSAGE = "A test message";
    private static final LocalDateTime CREATED = LocalDateTime.now();


    @Test
    public void testAnonymousCommentMapping() {
        // Arrange.
        final Comment comment = this.buildAnonComment();
        final CommentEntry expected = this.buildAnonCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        this.assertCommentEntry(expected, actual);
    }


    @Test
    public void testUserCommentMapping() {
        // Arrange.
        final Comment comment = this.buildUserComment();
        final CommentEntry expected = this.buildUserCommentEntry();

        // Act.
        final CommentEntry actual = CommentEntry.from(comment);

        // Assert.
        this.assertCommentEntry(expected, actual);
    }


    private void assertCommentEntry(
            final CommentEntry expected, final CommentEntry actual) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getCreated())
            .isEqualByComparingTo(expected.getCreated());
        assertThat(actual.getAnonName()).isEqualTo(expected.getAnonName());
        assertThat(actual.getMessage()).isEqualTo(expected.getMessage());
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
    }


    private CommentBuilder getCommentBuilder() {
        return new CommentBuilder()
                .created(CREATED).name(ANON_NAME).message(MESSAGE);
    }


    private User buildUser() {
        return new UserBuilder()
                .username(USERNAME).password(PASSWORD).email(EMAIL)
                .active(true).build();
    }


    private Comment buildAnonComment() {
        return this.getCommentBuilder().user(null).build();
    }


    private Comment buildUserComment() {
        return this.getCommentBuilder()
                .name(null)
                .user(this.buildUser())
                .build();
    }


    private CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .anonName(ANON_NAME).created(CREATED).message(MESSAGE)
                .build();
    }


    private CommentEntry buildUserCommentEntry() {
        return new CommentEntryBuilder()
                .anonName(null).created(CREATED).message(MESSAGE)
                .username(USERNAME).build();
    }
}
