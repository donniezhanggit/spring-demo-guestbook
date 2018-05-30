package gb.endpoints;

import static gb.testlang.fixtures.UsersFixtures.USERNAME;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Nullable;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import gb.common.guard.Guard;
import gb.common.it.EndpointITCase;
import lombok.val;


@WebMvcTest(IllegalArgumentAdviceTests.TestController.class)
@WithMockUser(username=USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class IllegalArgumentAdviceTests extends EndpointITCase {
    private static final String ILLEGAL_ARGUMENT_URL =
            "/test/IllegalArgument/messages";
    private static final String INVALID_ARGUMENT_URL =
            "/test/InvalidArgument/messages";
    private static final String MESSAGE = "message must not be null or empty";


    @Test
    public void IllegalArgumentException_should_convert_to_400_status()
            throws Exception {
        mockMvc.perform(get(ILLEGAL_ARGUMENT_URL))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void InvalidArgumentException_should_convert_to_400_status()
            throws Exception {
        mockMvc.perform(get(INVALID_ARGUMENT_URL))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void IllegalArgumentException_should_map_to_JSON()
            throws Exception {
        // Arrange.
        val code = "BAD_REQUEST";
        final String expected = buildExpectedJsonError(code, MESSAGE);

        // Act and assert.
        mockMvc.perform(get(ILLEGAL_ARGUMENT_URL))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(content().string(is(expected)));
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
        @GetMapping(ILLEGAL_ARGUMENT_URL)
        public void throwIllegalArgumentException() {
            Assert.isTrue(false, MESSAGE);
        }


        @GetMapping(INVALID_ARGUMENT_URL)
        public void throwInvalidArgumentException() {
            Guard.that(false, "BLANK_MESSAGE", MESSAGE);
        }
    }
}
