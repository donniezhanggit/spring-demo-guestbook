package gb.endpoints.common;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Nullable;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import gb.common.it.EndpointITCase;
import gb.common.validation.Check;
import lombok.val;


@WebMvcTest(InvalidArgumentAdviceTests.TestController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class InvalidArgumentAdviceTests extends EndpointITCase {
    private static final String INVALID_ARGUMENT_URL =
            "/test/InvalidArgument/messages";
    private static final String MESSAGE = "message must not be null or empty";


    @Test
    public void InvalidArgumentException_should_convert_to_400_status()
            throws Exception {
        mockMvc.perform(get(INVALID_ARGUMENT_URL))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void InvalidArgumentException_should_map_to_JSON()
            throws Exception {
        // Arrange.
        val code = "BLANK_MESSAGE";
        final String expected = buildExpectedJsonError(code, MESSAGE);

        // Act and assert.
        mockMvc.perform(get(INVALID_ARGUMENT_URL))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(content().string(is(expected)));
    }


    private String buildExpectedJsonError(@Nullable final String code,
            @Nullable final String message) {
        val error = ImmutableMap.of(
                "code", code,
                "message", message
        );

        return jsonify(error);
    }


    @RestController
    public static class TestController {
        @GetMapping(INVALID_ARGUMENT_URL)
        public void throwInvalidArgumentException() {
            Check.that(false, "BLANK_MESSAGE", MESSAGE);
        }
    }
}
