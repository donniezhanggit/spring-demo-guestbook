package gb.common.dto;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.junit.Test;

import gb.common.JUnitTestCase;
import lombok.Value;
import lombok.val;
import lombok.experimental.FieldDefaults;


// TODO: need to rework
public class ValidationErrorsTests extends JUnitTestCase {
    @Test
    public void test() {
        String path = "message";
        String message = "must not be null";
        val violation = buildConstraintViolationStub(
                buildValidationPathStub(path), message
        );
        val violationSet = Collections.singleton(violation);
        val exception = new ConstraintViolationException(violationSet);

        val actual = new ValidationErrors(exception);

        assertThat(actual.getErrors()).element(0).extracting(
                CodeAndMessage::getCode,
                CodeAndMessage::getMessage
        ).containsExactly(path, message);
    }


    // Do not want to use hibernate implementation of ConstraintViolation
    // interface. Let's stub it!
    private ConstraintViolation<TestInput>
    buildConstraintViolationStub(final Path path, final String message) {
        final ConstraintViolation<TestInput> cv =
                mock(ConstraintViolationTestInput.class);

        when(cv.getPropertyPath()).thenReturn(path);
        when(cv.getMessage()).thenReturn(message);

        return cv;
    }


    private Path buildValidationPathStub(final String pathAsString) {
        final Path path = mock(Path.class);

        when(path.toString()).thenReturn(pathAsString);

        return path;
    }


    private interface ConstraintViolationTestInput
    extends ConstraintViolation<TestInput> {

    }


    @Value
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    private static final class TestInput {
        private String name;
        private String message;
    }
}
