package gb.model;

import static gb.testlang.fixtures.CommentsFixtures.ANON_NAME;
import static gb.testlang.fixtures.CommentsFixtures.filledCommentBuilder;
import static gb.testlang.fixtures.UsersFixtures.stubUser;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;

import gb.common.JUnitTestCase;
import lombok.val;


public class CommentTests extends JUnitTestCase {
    @Test
    public void Null_as_commentBuilder_should_throw_NPE() {
        // Act.
        val throwable = catchThrowable(() -> new Comment(null));

        // Assert.
        assertThat(throwable).isInstanceOf(NullPointerException.class);
    }


    @Test
    public void No_anonName_and_no_username_throws_IllegalArgumentException() {
        // Arrange.
        final CommentBuilder builder = filledCommentBuilder()
                .anonName(null).user(Optional.empty());

        // Act.
        val throwable = catchThrowable(() -> new Comment(builder));

        // Assert.
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void When_user_was_provided_getting_anonName_should_return_null() {
        // Arrange.
        final CommentBuilder builder = filledCommentBuilder()
                .anonName(ANON_NAME).user(stubUser());

        // Act.
        final Comment newComment = new Comment(builder);

        // Assert.
        assertThat(newComment.getAnonName()).isNull();
    }


    @Test
    public void Comment_should_fill_date_created_when_builder_has_no_value() {
        // Arrange.
        final CommentBuilder builder = filledCommentBuilder().created(null);

        // Act.
        final Comment newComment = new Comment(builder);

        // Assert.
        assertThat(newComment.getCreated())
            .isCloseTo(LocalDateTime.now(), byLessThan(1, SECONDS));
    }
}
