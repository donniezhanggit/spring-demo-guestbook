package gb.dto;

import static gb.common.FakeData.stringWithLength;
import static gb.common.ValidationSubstrings.LENGTH_MUST_BE_BETWEEN;
import static gb.common.ValidationSubstrings.MUST_NOT_BE_EMPTY;
import static gb.domain.FullName.FIRST_NAME_MAX_LENGTH;
import static gb.domain.FullName.FIRST_NAME_MIN_LENGTH;
import static gb.domain.FullName.LAST_NAME_MAX_LENGTH;
import static gb.domain.FullName.LAST_NAME_MIN_LENGTH;
import static gb.dto.FullNameInput.FIRST_NAME_FIELD;
import static gb.dto.FullNameInput.LAST_NAME_FIELD;
import static gb.testlang.fixtures.FullNameFixtures.filledFullNameInputBuilder;

import org.junit.Test;

import gb.common.BeanValidationTestCase;


public class FullNameInputValidationTests extends BeanValidationTestCase {
    @Test
    public void When_firstName_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongFirstName =
                stringWithLength(FIRST_NAME_MAX_LENGTH+1);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(tooLongFirstName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(FIRST_NAME_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_firstName_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longFirstName = stringWithLength(FIRST_NAME_MAX_LENGTH);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(longFirstName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_lastName_is_too_long_expect_validation_error() {
        // Arrange.
        final String tooLongLastName =
                stringWithLength(LAST_NAME_MAX_LENGTH+1);
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(tooLongLastName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(LAST_NAME_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_lastName_is_max_expect_no_validation_errors() {
        // Arrange.
        final String longLastName = stringWithLength(LAST_NAME_MAX_LENGTH);
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(longLastName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_firstName_is_too_short_expect_validation_error() {
        // Arrange.
        final String tooShortFirstName =
                stringWithLength(FIRST_NAME_MIN_LENGTH-1);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(tooShortFirstName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(FIRST_NAME_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_firstName_is_min_expect_no_validation_errors() {
        // Arrange.
        final String shortFirstName =
                stringWithLength(FIRST_NAME_MIN_LENGTH);
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(shortFirstName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_lastName_is_too_short_expect_validation_error() {
        // Arrange.
        final String tooShortLastName =
                stringWithLength(LAST_NAME_MIN_LENGTH-1);
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(tooShortLastName).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(LAST_NAME_FIELD)
            .withMessageContaining(LENGTH_MUST_BE_BETWEEN);
    }


    @Test
    public void When_lastName_is_min_expect_no_validation_errors() {
        // Arrange.
        final String shortLastName =
                stringWithLength(LAST_NAME_MIN_LENGTH);
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(shortLastName).build();

        // Act and assert.
        check(input).hasNoErrors();
    }


    @Test
    public void When_firstName_is_null_expect_validation_error() {
        // Arrange.
        final FullNameInput input = filledFullNameInputBuilder()
                .firstName(null).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(FIRST_NAME_FIELD)
            .withMessageContaining(MUST_NOT_BE_EMPTY);
    }


    @Test
    public void When_lastName_is_null_expect_validation_error() {
        // Arrange.
        final FullNameInput input = filledFullNameInputBuilder()
                .lastName(null).build();

        // Act and assert.
        check(input).hasOnlyOneError()
            .forProperty(LAST_NAME_FIELD)
            .withMessageContaining(MUST_NOT_BE_EMPTY);
    }
}
