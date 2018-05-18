package gb.test.dto;

import static gb.model.Comment.MESSAGE_MAX_LENGTH;
import static gb.model.Comment.NAME_MAX_LENGTH;
import static gb.test.common.FakeData.stringWithLength;
import static gb.test.fixtures.CommentsFixtures.commentInputBuilderWithNameAndMessage;

import org.junit.Test;

import gb.dto.CommentInput;
import gb.test.common.BeanValidationTestCase;


public class CommentInputValidationTests extends BeanValidationTestCase {
    @Test
    public void When_message_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongMessage = stringWithLength(MESSAGE_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(tooLongMessage).build();

        // Act and assert.
        check(input).hasOnlyOneError();
    }


    @Test
    public void When_message_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longMessage = stringWithLength(MESSAGE_MAX_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(longMessage).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_name_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongName = stringWithLength(NAME_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(tooLongName).build();

        // Act and assert.
        check(input).hasOnlyOneError();
    }


    @Test
    public void When_name_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longName = stringWithLength(NAME_MAX_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(longName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_name_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(null).build();

        // Act and assert.
        check(input).hasOnlyOneError();
    }


    @Test
    public void When_message_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(null).build();

        // Act and assert.
        check(input).hasOnlyOneError();
    }
}
