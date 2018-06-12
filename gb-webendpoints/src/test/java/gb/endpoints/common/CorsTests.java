package gb.endpoints.common;

import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import gb.common.it.EndpointITCase;


@WebMvcTest(CorsTests.TestController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class CorsTests extends EndpointITCase {
    private static final String PING_API_URL = "/api/ping";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String WRONG_ORIGIN_URL = "http://malicious.net";
    private static final String RIGHT_ORIGIN_URL = "http://localhost:8080";


    @Test
    public void Request_with_wrong_origin_should_return_403()
            throws Exception {
        mockMvc.perform(get(PING_API_URL)
                .header(ORIGIN_HEADER, WRONG_ORIGIN_URL))
            .andExpect(status().isForbidden());
    }


    @Test
    public void Request_with_right_origin_should_return_200()
            throws Exception {
        mockMvc.perform(get(PING_API_URL)
                .header(ORIGIN_HEADER, RIGHT_ORIGIN_URL))
            .andExpect(status().isOk());
    }


    @RestController
    public static class TestController {
        @GetMapping(path=PING_API_URL, produces=APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<String> pong() {
            return ResponseEntity.ok(null);
        }
    }
}
