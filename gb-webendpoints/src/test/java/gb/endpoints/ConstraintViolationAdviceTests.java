package gb.endpoints;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import gb.common.it.EndpointITCase;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.val;
import lombok.experimental.FieldDefaults;


@WebMvcTest(ConstraintViolationAdviceTests.TestController.class)
@WithMockUser(username="testUser", roles={"USER", "ADMIN", "ACTUATOR"})
public class ConstraintViolationAdviceTests extends EndpointITCase {
    private static final String API_URL = "/api/comments";


    @Test
    public void ConstraintViolationException_should_convert_to_412_status()
            throws Exception {
        // Arrange.
        val invalidComment = new CommentInput("");
        final String invalidCommentJson = jsonify(invalidComment);

        // Act and assert.
        mockMvc.perform(post(API_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidCommentJson))
            .andExpect(status().isPreconditionFailed());
    }


    @Test
    public void ConstraintViolationException_should_map_to_JSON()
            throws Exception {
        // Arrange.
        val invalidComment = new CommentInput(null);
        final String invalidCommentJson = jsonify(invalidComment);
        final String expected = buildExpectedValidationError();

        // Act and assert.
        mockMvc.perform(post(API_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidCommentJson))
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


    @Value
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    private static class CommentInput {
        @NotNull
        String message;
    }


    @Service
    @Validated
    public static class TestService {
        public long createComment(@Valid CommentInput input) {
            return 1L;  // Do nothing. Just return dumb id.
        }
    }


    @RequestMapping(API_URL)
    @RestController
    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE, makeFinal=true)
    public static class TestController {
        @NonNull TestService service;


        @PostMapping
        @ResponseStatus(OK)
        public long createComment(final CommentInput input) {
            return service.createComment(input);
        }
    }
}
