package gb.test.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.Test;

import gb.model.Comment;
import gb.model.CommentBuilder;
import gb.model.User;
import gb.model.UserBuilder;
import gb.test.common.JUnitTestCase;


public class CommentBuilderTests extends JUnitTestCase {
    private static final String ANON_NAME = "Just name";
    private static final String MESSAGE = "Just message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final String USERNAME = "User123";
    private static final String PASSWORD = "VeryS3cureP4ssw0rD";
    private static final String EMAIL = "admin@mail.org";


    @Test
    public void A_builder_should_return_a_new_comment() {
        // Arrange and act.
        final Comment actual = this.getCommentBuilder().user(null).build();

        // Assert.
        this.assertAnonComment(actual);
    }


    @Test
    public void A_builder_with_user_should_return_a_new_comment_with_user() {
        // Arrange.
        final User user = this.buildUser();

        // Act.
        final Comment actual = this.getCommentBuilder().user(user).build();

        // Assert.
        this.assertUserComment(actual);
    }


    private void assertAnonComment(final Comment actual) {
        assertThat(actual.getUser().isPresent()).isFalse();

        this.assertComment(actual);
    }


    private void assertUserComment(final Comment actual) {
        assertThat(actual.getUser().isPresent()).isTrue();

        this.assertComment(actual);
    }


    private void assertComment(final Comment actual) {
        assertThat(actual.getCreated()).isEqualByComparingTo(CREATED);
        assertThat(actual.getName()).isEqualTo(ANON_NAME);
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
    }


    private User buildUser() {
        return new UserBuilder()
                .username(USERNAME).password(PASSWORD)
                .email(EMAIL).active(true).build();
    }


    private CommentBuilder getCommentBuilder() {
        return new CommentBuilder()
                .name(ANON_NAME).message(MESSAGE)
                .user(null).created(CREATED);
    }
}
