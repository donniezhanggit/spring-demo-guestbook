package gb.test.it.endpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import gb.test.it.common.EndpointITCase;


public class CorsTests extends EndpointITCase {
    private static final String PING_API_URL = "/api/ping";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String WRONG_ORIGIN_URL = "http://malicious.net";
    private static final String RIGHT_ORIGIN_URL = "http://localhost:8080";


    @Test
    public void Request_with_wrong_origin_should_return_403()
            throws Exception {
        this.mockMvc.perform(get(PING_API_URL)
                .header(ORIGIN_HEADER, WRONG_ORIGIN_URL))
            .andExpect(status().isForbidden());
    }


    @Test
    public void Request_with_right_origin_should_return_200()
            throws Exception {
        this.mockMvc.perform(get(PING_API_URL)
                .header(ORIGIN_HEADER, RIGHT_ORIGIN_URL))
            .andExpect(status().isOk());
    }
}
