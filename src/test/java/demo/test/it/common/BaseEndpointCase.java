package demo.test.it.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.config.GuestBookProfiles;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@ActiveProfiles(profiles=GuestBookProfiles.H2_INTEGRATION_TESTING)
public abstract class BaseEndpointCase {
    private final Logger logger = LoggerFactory
            .getLogger(BaseEndpointCase.class.getName());

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected MockHttpServletRequest request;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;


    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac).build();

        this.setup();
    }


    protected String jsonStringify(final Object o) {
        try {
            return this.stringifyOrThrow(o);
        } catch (JsonProcessingException e) {
            // Do not force an end user to handle exceptions.
            throw new RuntimeException(e);
        }
    }


    private String stringifyOrThrow(final Object o)
            throws JsonProcessingException {
        final String json = this.objectMapper.writeValueAsString(o);

        logger.info("Object as JSON:\n" + json);

        return json;
    }


    protected abstract void setup();
}
