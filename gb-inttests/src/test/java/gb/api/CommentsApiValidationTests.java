package gb.api;

import static gb.common.FakeData.stringWithLength;
import static gb.fixtures.CommentsFixtures.commentInputBuilderWithNameAndMessage;
import static gb.model.Comment.MESSAGE_MAX_LENGTH;
import static gb.model.Comment.NAME_MAX_LENGTH;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.it.RecreatePerClassITCase;
import gb.dto.CommentInput;
import lombok.val;
import lombok.experimental.FieldDefaults;



@FieldDefaults(level=PRIVATE)
@WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
public class CommentsApiValidationTests extends RecreatePerClassITCase {
    @Autowired
    CommentsApi commentsApi;


    @Test
    public void When_name_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongName = stringWithLength(NAME_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(tooLongName).build();

        // Act.
        val throwable = catchThrowable(() -> commentsApi.createComment(input));

        // Assert.
        assertThat(throwable).isInstanceOf(ValidationException.class);
    }


    @Test
    public void When_name_length_is_max_comment_should_be_saved() {
        //Arrange.
        final String longName = stringWithLength(NAME_MAX_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(longName).build();

        // Act.
        commentsApi.createComment(input);
    }


    @Test
    public void When_message_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongMessage = stringWithLength(MESSAGE_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(tooLongMessage).build();

        // Act.
        val throwable = catchThrowable(() -> commentsApi.createComment(input));

        // Assert.
        assertThat(throwable).isInstanceOf(ValidationException.class);
    }


    @Test
    public void When_message_length_is_max_comment_should_be_saved() {
        // Arrange.
        final String longMessage = stringWithLength(MESSAGE_MAX_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(longMessage).build();

        // Act.
        commentsApi.createComment(input);
    }


    @Test
    public void When_name_is_null_expect_ValidationException() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(null).build();

        // Act.
        val throwable = catchThrowable(() -> commentsApi.createComment(input));

        // Assert.
        assertThat(throwable).isInstanceOf(ValidationException.class);
    }


    @Test
    public void When_message_is_null_expect_ValidationException() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(null).build();

        // Act.
        val throwable = catchThrowable(() -> commentsApi.createComment(input));

        // Assert.
        assertThat(throwable).isInstanceOf(ValidationException.class);
    }
}
