package gb.endpoints.common;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import gb.common.it.EndpointITCase;
import gb.common.validation.Check;


@WebMvcTest(InvalidArgumentAdviceTests.TestController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class InvalidArgumentAdviceTests extends EndpointITCase {
    private static final String INVALID_ARGUMENT_URL =
            "/test/InvalidArgument/messages";
    private static final String FIELD_PATH = "$.errors[0].field";
    private static final String CODE_PATH = "$.errors[0].code";
    private static final String CODE_VALUE = "BLANK_MESSAGE";
    private static final String MESSAGE_PATH = "$.errors[0].message";
    private static final String MESSAGE_VALUE = "must not be null";


    @Test
    public void InvalidArgumentException_should_convert_to_422_status()
            throws Exception {
        mockMvc.perform(get(INVALID_ARGUMENT_URL))
            .andExpect(status().isUnprocessableEntity());
    }


    @Test
    public void InvalidArgumentException_should_map_to_JSON()
            throws Exception {

        mockMvc.perform(get(INVALID_ARGUMENT_URL))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath(FIELD_PATH, nullValue()))
            .andExpect(jsonPath(CODE_PATH, is(CODE_VALUE)))
            .andExpect(jsonPath(MESSAGE_PATH, is(MESSAGE_VALUE)));
    }


    @RestController
    public static class TestController {
        @GetMapping(INVALID_ARGUMENT_URL)
        public void throwInvalidArgumentException() {
            Check.that(false, CODE_VALUE, MESSAGE_VALUE);
        }
    }
}
