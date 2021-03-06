package gb.endpoints.common;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import gb.common.it.EndpointITCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;


@WebMvcTest(ConstraintViolationAdviceTests.TestController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class ConstraintViolationAdviceTests extends EndpointITCase {
    private static final String API_URL = "/test/messages";
    private static final String FIELD_PATH = "$.errors[0].field";
    private static final String FIELD_VALUE = "message";
    private static final String CODE_PATH = "$.errors[0].code";
    private static final String CODE_VALUE = "NotNull";
    private static final String MESSAGE_PATH = "$.errors[0].message";
    private static final String MESSAGE_VALUE = "must not be null";


    @Test
    public void ConstraintViolationException_should_convert_to_422_status()
            throws Exception {
        // Arrange.
        val invalidInput = new TestInput(null);
        final String invalidInputJson = jsonify(invalidInput);

        // Act and assert.
        mockMvc.perform(post(API_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidInputJson))
            .andExpect(status().isUnprocessableEntity());
    }


    @Test
    public void ConstraintViolationException_should_map_to_JSON()
            throws Exception {
        // Arrange.
        val invalidInput = new TestInput(null);
        final String invalidInputJson = jsonify(invalidInput);

        // Act and assert.
        mockMvc.perform(post(API_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(invalidInputJson))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath(FIELD_PATH, is(FIELD_VALUE)))
                .andExpect(jsonPath(CODE_PATH, is(CODE_VALUE)))
                .andExpect(jsonPath(MESSAGE_PATH, is(MESSAGE_VALUE)));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level=PRIVATE)
    @FieldNameConstants
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
