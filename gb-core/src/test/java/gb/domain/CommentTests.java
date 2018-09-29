package gb.domain;

import static gb.common.DomainEventChecker.checkThat;
import static gb.testlang.fixtures.CommentsFixtures.ANON_NAME;
import static gb.testlang.fixtures.CommentsFixtures.MESSAGE;
import static gb.testlang.fixtures.CommentsFixtures.filledCommentBuilder;
import static gb.testlang.fixtures.UsersFixtures.stubUser;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.byLessThan;

import java.time.LocalDateTime;

import org.junit.Test;

import gb.common.JUnitTestCase;
import gb.common.validation.InvalidArgumentException;


public class CommentTests extends JUnitTestCase {
    @Test
    public void Null_as_commentBuilder_should_throw_IAE() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Comment(null));
    }


    @Test
    public void No_anonName_and_no_username_throws_InvalidArgumentException() {
        // Arrange.
        final CommentBuilder builder = filledCommentBuilder()
                .anonName(null).user(null);

        // Act and assert.
        assertThatExceptionOfType(InvalidArgumentException.class)
            .isThrownBy(() -> new Comment(builder));
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
        final CommentBuilder builder = filledCommentBuilder().createdAt(null);

        // Act.
        final Comment newComment = new Comment(builder);

        // Assert.
        assertThat(newComment.getCreatedAt())
            .isCloseTo(LocalDateTime.now(), byLessThan(1, SECONDS));
    }


    @Test
    public void Instantiating_a_comment_should_generate_event() {
        // Arrange.
        final CommentBuilder builder = filledCommentBuilder()
                .anonName(ANON_NAME).message(MESSAGE);

        // Act.
        final Comment newComment = new Comment(builder);

        //Assert.
        checkThat(newComment).hasOnlyOneEvent()
            .whichIsInstanceOf(NewCommentAdded.class)
            .hasFieldEquals(NewCommentAdded::getAuthorName, ANON_NAME)
            .hasFieldEquals(NewCommentAdded::getMessage, MESSAGE);
    }
}
