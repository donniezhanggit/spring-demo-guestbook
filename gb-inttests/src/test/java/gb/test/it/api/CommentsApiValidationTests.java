package gb.test.it.api;

import static gb.test.common.FakeData.stringWithLength;
import static gb.test.fixtures.CommentsFixtures.commentInputBuilderWithNameAndMessage;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.CommentsApi;
import gb.dto.CommentInput;
import gb.model.Comment;
import gb.test.it.common.RecreatePerClassITCase;


public class CommentsApiValidationTests extends RecreatePerClassITCase {
    @Autowired
    private CommentsApi commentsApi;


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_name_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongName = stringWithLength(
                Comment.NAME_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(tooLongName).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        commentsApi.createComment(input);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_name_length_is_max_comment_should_be_saved() {
        //Arrange.
        final String longName = stringWithLength(
                Comment.NAME_MAX_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(longName).build();

        // Act.
        commentsApi.createComment(input);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_message_is_too_long_expect_ValidationException() {
        // Arrange.
        final String tooLongMessage = stringWithLength(
                Comment.MESSAGE_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(tooLongMessage).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        commentsApi.createComment(input);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_message_length_is_max_comment_should_be_saved() {
        // Arrange.
        final String longMessage = stringWithLength(
                Comment.MESSAGE_MAX_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(longMessage).build();

        // Act.
        commentsApi.createComment(input);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_name_is_null_expect_ValidationException() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(null).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        commentsApi.createComment(input);
    }


    @Test
    @WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
    public void When_message_is_null_expect_ValidationException() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(null).build();

        // Act and assert.
        thrown.expect(ValidationException.class);
        commentsApi.createComment(input);
    }
}
