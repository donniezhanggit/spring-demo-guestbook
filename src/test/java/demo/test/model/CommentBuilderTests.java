package demo.test.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import demo.model.Comment;
import demo.model.CommentBuilder;
import demo.model.User;
import demo.model.UserBuilder;

public class CommentBuilderTests {
    private static final String NAME = "Just name";
    private static final String MESSAGE = "Just message";

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void A_builder_should_return_a_new_comment() {
        // Arrange and act.
        final Comment actual = new CommentBuilder()
                .name(NAME).message(MESSAGE).user(null)
                .created(LocalDateTime.now()).build();

        // Assert.
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getVersion()).isNull();
        assertThat(actual.getUser().isPresent()).isFalse();
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
        assertThat(actual.getCreated()).isNotNull();
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getMessage()).isEqualTo(MESSAGE);
        assertThat(actual.getId()).isNull();
        assertThat(actual.getVersion()).isNull();
        assertThat(actual.getUser().isPresent()).isTrue();
    }
}
