package gb.endpoints.common;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import gb.common.it.EndpointITCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;


@WebMvcTest(ConstraintViolationAdviceTests.TestController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class ConstraintViolationAdviceTests extends EndpointITCase {
    private static final String API_URL = "/test/messages";


    @Test
    public void ConstraintViolationException_should_convert_to_412_status()
            throws Exception {
        // Arrange.
        val invalidInput = new TestInput(null);
        final String invalidInputJson = jsonify(invalidInput);

        // Act and assert.
        mockMvc.perform(post(API_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidInputJson))
            .andExpect(status().isPreconditionFailed());
    }


    @Test
    public void ConstraintViolationException_should_map_to_JSON()
            throws Exception {
        // Arrange.
        val invalidInput = new TestInput(null);
        final String invalidInputJson = jsonify(invalidInput);
        final String expected = buildExpectedValidationError();

        // Act and assert.
        mockMvc.perform(post(API_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidInputJson))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(content().string(is(expected)));
    }


    private String buildExpectedValidationError() {
        final Map<String, String> error = ImmutableMap.of(
                "code", "createComment.input.message",
                "message", "must not be null"
        );

        return jsonify(ImmutableMap.of("errors", Arrays.asList(error)));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE)
    public static class TestInput {
        @NotNull
        private String message;
    }


    @Service
    @Validated
    public static class TestService {
        public long createComment(@Valid TestInput input) {
            return 1L;  // Do nothing. Just return dumb id.
        }
    }


    @RestController
    @RequestMapping(API_URL)
    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    public static class TestController {
        @NonNull TestService service;


        @PostMapping
        @ResponseStatus(CREATED)
        public long createComment(@RequestBody final TestInput input) {
            return service.createComment(input);
        }
    }
}
