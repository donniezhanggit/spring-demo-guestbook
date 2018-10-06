package gb.common.ft;

import static gb.common.config.GuestBookProfiles.FUNCTIONAL_TESTING;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import gb.Application;
import gb.services.CurrentPrincipalService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;


@Log4j2
@ActiveProfiles(profiles=FUNCTIONAL_TESTING)
@ComponentScan(basePackageClasses=Application.class)
@SpringBootTest(webEnvironment=MOCK)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public abstract class CucumberFTCase {
    protected static ResponseResults latestResponse = null;

    //@Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;


    protected MockMvc mockMvc;

    @MockBean
    public CurrentPrincipalService currentPrincipalService;


    @SneakyThrows
    protected String jsonify(final Object o) {
        final String json = objectMapper.writeValueAsString(o);

        log.info("Object as JSON:\n{}", json);

        return json;
    }


    @SneakyThrows
    protected void executeGet(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }


    @SneakyThrows
    protected void executePost(String url, String body) {
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        requestCallback.setBody(body);

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate
          .execute(url, HttpMethod.POST, requestCallback, response -> {
              if (errorHandler.hadError) {
                  return (errorHandler.getResults());
              } else {
                  return (new ResponseResults(response));
              }
          });
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults(response);
        }
    }


    @TestConfiguration
    public static class Config {
        @Bean
        private void currentPrincipalService() {
            CurrentPrincipalService cps = Mockito.mock(CurrentPrincipalService.class);
            when(cps.getCurrentUser()).thenReturn(Optional.ofNullable(null));
        }
    }
}
