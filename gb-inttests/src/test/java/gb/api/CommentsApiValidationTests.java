package gb.api;

import static gb.common.FakeData.stringWithLength;
import static gb.model.Comment.ANON_NAME_MAX_LENGTH;
import static gb.model.Comment.MESSAGE_MAX_LENGTH;
import static gb.testlang.fixtures.CommentsFixtures.filledCommentInputBuilder;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.common.exceptions.InvalidArgumentException;
import gb.common.it.RecreatePerClassITCase;
import gb.dto.CommentInput;
import gb.testlang.fixtures.UsersFixtures;
import lombok.val;
import lombok.experimental.FieldDefaults;



@FieldDefaults(level=PRIVATE)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class CommentsApiValidationTests extends RecreatePerClassITCase {
    @Autowired
    CommentsApi commentsApi;

    @Autowired
    UsersFixtures usersFixtures;


    @Test
    public void When_anonName_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongName = stringWithLength(ANON_NAME_MAX_LENGTH+1);
        final CommentInput input = filledCommentInputBuilder()
                .anonName(tooLongName).build();

        // Act and assert.
        assertThatInvalid(() -> commentsApi.createComment(input));
    }


    @Test
    public void When_anonName_length_is_max_comment_should_be_saved() {
        //Arrange.
        final String longAnonName = stringWithLength(ANON_NAME_MAX_LENGTH);
        final CommentInput input = filledCommentInputBuilder()
                .anonName(longAnonName).build();

        // Act. Must not throw.
        commentsApi.createComment(input);
    }


    @Test
    public void When_message_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongMessage = stringWithLength(MESSAGE_MAX_LENGTH+1);
        final CommentInput input = filledCommentInputBuilder()
                .message(tooLongMessage).build();

        // Act and assert.
        assertThatInvalid(() -> commentsApi.createComment(input));
    }


    @Test
    public void When_message_length_is_max_comment_should_be_saved() {
        // Arrange.
        final String longMessage = stringWithLength(MESSAGE_MAX_LENGTH);
        final CommentInput input = filledCommentInputBuilder()
                .message(longMessage).build();

        // Act. Must not throw.
        commentsApi.createComment(input);
    }


    @Test
    public void When_name_is_null_expect_InvalidArgumentException() {
        // Arrange.
        usersFixtures.recreateExistingUser();

        final CommentInput input = filledCommentInputBuilder()
                .anonName(null).build();

        // Act.
        val throwable = catchThrowable(() -> commentsApi.createComment(input));

        // Assert.
        // TODO: Does not exactly right. Should create a comment, because
        // current user is authenticated, but still thinking how to setup
        // security context in base class of integration tests.
        assertThat(throwable).isInstanceOf(InvalidArgumentException.class);
    }


    @Test
    public void When_message_is_null_expect_ValidationException() {
        // Arrange.
        final CommentInput input = filledCommentInputBuilder()
                .message(null).build();

        // Act and assert.
        assertThatInvalid(() -> commentsApi.createComment(input));
    }
}
