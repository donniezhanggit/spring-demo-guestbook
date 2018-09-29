package gb.dto;

import static gb.common.FakeData.stringWithLength;
import static gb.common.ValidationSubstrings.LENGTH_MUST_BE_BETWEEN;
import static gb.common.ValidationSubstrings.MUST_NOT_BE_NULL;
import static gb.domain.Comment.ANON_NAME_MAX_LENGTH;
import static gb.domain.Comment.ANON_NAME_MIN_LENGTH;
import static gb.domain.Comment.MESSAGE_MAX_LENGTH;
import static gb.domain.Comment.MESSAGE_MIN_LENGTH;
import static gb.dto.CommentInput.ANON_NAME_FIELD;
import static gb.dto.CommentInput.MESSAGE_FIELD;
import static gb.testlang.fixtures.CommentsFixtures.filledCommentInputBuilder;

import org.junit.Test;

import gb.common.BeanValidationTestCase;


public class CommentInputValidationTests extends BeanValidationTestCase {
    @Test
    public void When_message_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongMessage = stringWithLength(MESSAGE_MAX_LENGTH+1);
        final CommentInput input = filledCommentInputBuilder()
                .message(tooLongMessage).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(MESSAGE_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_message_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longMessage = stringWithLength(MESSAGE_MAX_LENGTH);
        final CommentInput input = filledCommentInputBuilder()
                .message(longMessage).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_anonName_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongName = stringWithLength(ANON_NAME_MAX_LENGTH+1);
        final CommentInput input = filledCommentInputBuilder()
                .anonName(tooLongName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(ANON_NAME_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_anonName_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longAnonName = stringWithLength(ANON_NAME_MAX_LENGTH);
        final CommentInput input = filledCommentInputBuilder()
                .anonName(longAnonName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_message_is_too_short_expect_validation_error() {
        // Arrange.
        final String tooShortMessage = stringWithLength(MESSAGE_MIN_LENGTH-1);
        final CommentInput input = filledCommentInputBuilder()
                .message(tooShortMessage).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(MESSAGE_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_message_is_min_expect_no_validation_errors() {
        // Arrange.
        final String shortMessage = stringWithLength(MESSAGE_MIN_LENGTH);
        final CommentInput input = filledCommentInputBuilder()
                .message(shortMessage).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_anonName_is_too_short_expect_validation_error() {
        // Arrange.
        final String tooShortName = stringWithLength(ANON_NAME_MIN_LENGTH-1);
        final CommentInput input = filledCommentInputBuilder()
                .anonName(tooShortName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(ANON_NAME_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_anonName_is_min_expect_no_validation_errors() {
        // Arrange.
        final String shortName = stringWithLength(ANON_NAME_MIN_LENGTH);
        final CommentInput input = filledCommentInputBuilder()
                .message(shortName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }



    @Test
    public void When_name_is_null_expect_no_validation_errors() {
        // Arrange.
        final CommentInput input = filledCommentInputBuilder()
                .anonName(null).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_message_is_null_expect_validation_error() {
        // Arrange.
        final CommentInput input = filledCommentInputBuilder()
                .message(null).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(MESSAGE_FIELD)
            .withMessageContaining(MUST_NOT_BE_NULL);
    }
}
