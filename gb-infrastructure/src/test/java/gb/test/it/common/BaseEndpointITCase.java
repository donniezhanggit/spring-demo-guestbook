package gb.test.it.common;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gb.common.config.GuestBookProfiles;
import gb.common.config.MainConfig;
import gb.common.config.SecurityConfig;


@RunWith(SpringRunner.class)
@ActiveProfiles(profiles=GuestBookProfiles.NO_DB_INTEGRATION_TESTING)
@Import({MainConfig.class, SecurityConfig.class})
@ComponentScan(basePackages="gb")
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public abstract class BaseEndpointITCase {
    private final Logger logger = LoggerFactory
            .getLogger(BaseEndpointITCase.class.getName());

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected MockHttpServletRequest request;

    protected MockMvc mockMvc;

    @MockBean
    protected UserDetailsService userDetailsService;


    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac).build();

        this.setupAuthentication();
        this.setup();
    }


    protected String jsonify(final Object o) {
        try {
            return this.jsonifyOrThrow(o);
        } catch (JsonProcessingException e) {
            // Do not force an end user to handle exceptions.
            throw new RuntimeException(e);
        }
    }


    private String jsonifyOrThrow(final Object o)
            throws JsonProcessingException {
        final String json = this.objectMapper.writeValueAsString(o);

        logger.info("Object as JSON:\n" + json);

        return json;
    }


    private void setupAuthentication() {
        @SuppressWarnings("unused")
        final Authentication authentication = mock(Authentication.class);
        final SecurityContext securityContext = mock(SecurityContext.class);

//        when(securityContext.getAuthentication())
//            .thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

//        when(this.userDetailsService.loadUserByUsername(any()))
//            .thenReturn(null);
    }


    protected abstract void setup();
}
