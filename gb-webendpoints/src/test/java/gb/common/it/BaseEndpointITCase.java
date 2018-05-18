package gb.common.it;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import gb.common.config.GuestBookProfiles;
import gb.common.config.MainConfig;
import gb.common.config.SecurityConfig;
import gb.config.WebConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles=GuestBookProfiles.NO_DB_INTEGRATION_TESTING)
@Import({
        MainConfig.class,
        SecurityConfig.class,
        WebConfig.class
})
public abstract class BaseEndpointITCase {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected UserDetailsService userDetailsService;


    @Before
    public void before() {
        setupAuthentication();
        setup();
    }


    @SneakyThrows
    protected String jsonify(final Object o) {
        final String json = objectMapper.writeValueAsString(o);

        log.info("Object as JSON:\n {}", json);

        return json;
    }


    private void setupAuthentication() {
        @SuppressWarnings("unused")
        final Authentication authentication = mock(Authentication.class);
        final SecurityContext securityContext = mock(SecurityContext.class);

//        when(securityContext.getAuthentication())
//            .thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

//        when(userDetailsService.loadUserByUsername(any()))
//            .thenReturn(null);
    }


    protected abstract void setup();
}
