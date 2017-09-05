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
    private static final String NAME = "Just name";
    private static final String MESSAGE = "Just message";


    @Test
    public void A_builder_should_return_a_new_comment() {
        // Arrange and act.
        final Comment actual = new CommentBuilder()
                .name(NAME).message(MESSAGE).user(null)
                .created(LocalDateTime.now()).build();

        // Assert.
        this.assertAnonComment(actual);
    }


    @Test
    public void A_builder_with_user_should_return_a_new_comment_with_user() {
        // Arrange.
        final User user = new UserBuilder()
                .username(NAME).password("password").active(true).build();

        // Act.
        final Comment actual = new CommentBuilder()
                .name(NAME).message(MESSAGE).user(user)
                .created(LocalDateTime.now()).build();

        // Assert.
        this.assertUserComment(actual);
    }


    private void assertAnonComment(final Comment actual) {
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getVersion()).isNull();
        assertThat(actual.getUser().isPresent()).isFalse();
    }


    private void assertUserComment(final Comment actual) {
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getVersion()).isNull();
        assertThat(actual.getUser().isPresent()).isTrue();
    }
}
