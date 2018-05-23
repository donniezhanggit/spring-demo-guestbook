package gb.dto;

import static gb.common.FakeData.stringWithLength;
import static gb.common.ValidationCheck.LENGTH_MUST_BE_BETWEEN;
import static gb.common.ValidationCheck.MUST_NOT_BE_NULL;
import static gb.model.Comment.MESSAGE_MAX_LENGTH;
import static gb.model.Comment.MESSAGE_MIN_LENGTH;
import static gb.model.Comment.NAME_MAX_LENGTH;
import static gb.model.Comment.NAME_MIN_LENGTH;
import static gb.testlang.fixtures.CommentsFixtures.commentInputBuilderWithNameAndMessage;

import org.junit.Test;

import gb.common.BeanValidationTestCase;


public class CommentInputValidationTests extends BeanValidationTestCase {
    @Test
    public void When_message_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongMessage = stringWithLength(MESSAGE_MAX_LENGTH+1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(tooLongMessage).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty("message")
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
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
        check(input).hasOnlyOneError()
            .forProperty("name")
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
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
    public void When_message_is_too_short_expect_validation_error() {
        // Arrange.
        final String tooShortMessage = stringWithLength(MESSAGE_MIN_LENGTH-1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(tooShortMessage).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty("message")
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_message_is_min_expect_no_validation_errors() {
        // Arrange.
        final String shortMessage = stringWithLength(MESSAGE_MIN_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(shortMessage).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_name_is_too_short_expect_validation_error() {
        // Arrange.
        final String tooShortName = stringWithLength(NAME_MIN_LENGTH-1);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(tooShortName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty("name")
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_name_is_min_expect_no_validation_errors() {
        // Arrange.
        final String shortName = stringWithLength(NAME_MIN_LENGTH);
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(shortName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }



    @Test
    public void When_name_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .name(null).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty("name")
            .withMessageContaining(MUST_NOT_BE_NULL);
    }


    @Test
    public void When_message_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = commentInputBuilderWithNameAndMessage()
                .message(null).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty("message")
            .withMessageContaining(MUST_NOT_BE_NULL);
    }
}
