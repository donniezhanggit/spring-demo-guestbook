package gb.endpoints.common;

import static gb.common.exceptions.Exceptions.notFound;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static org.hamcrest.Matchers.isEmptyString;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gb.common.it.EndpointITCase;


@WebMvcTest(NotFoundAdviceTests.TestController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN"})
public class NotFoundAdviceTests extends EndpointITCase {
    private static final String API_URL = "/test/messages";
    private static final String NON_EXISTENT_MESSAGE_URL =
            API_URL + "/" + Long.MAX_VALUE;


    @Test
    public void NotFoundException_should_convert_to_404_status()
            throws Exception {
        mockMvc.perform(delete(NON_EXISTENT_MESSAGE_URL))
            .andExpect(status().isNotFound())
            .andExpect(content().string(isEmptyString()));
    }


    @RestController
    @RequestMapping(API_URL)
    public static class TestController {
        @DeleteMapping("/{id}")
        @ResponseStatus(NO_CONTENT)
        public void createComment(@PathVariable long id) {
            throw notFound(id);
        }
    }
}
