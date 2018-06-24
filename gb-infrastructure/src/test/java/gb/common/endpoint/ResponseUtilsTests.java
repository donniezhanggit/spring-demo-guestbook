package gb.common.endpoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.junit.Test;
import org.springframework.http.ResponseEntity;


public class ResponseUtilsTests {
    private static final int BODY = 42;
    private static final Optional<Integer> YES = Optional.of(BODY);
    private static final Optional<Integer> NO = Optional.empty();


    @Test
    public void An_optional_with_value_should_convert_to_200_ok() {
        // Act.
        final ResponseEntity<Integer> response =
                ResponseUtils.wrapOrNotFound(YES);

        // Assert.
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(BODY);
    }


    @Test
    public void An_empty_optional_should_convert_to_404_not_found() {
        // Act.
        final ResponseEntity<Integer> response =
                ResponseUtils.wrapOrNotFound(NO);

        // Assert.
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
