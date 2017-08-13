package demo.test.it.endpoints;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import demo.api.CommentsApi;
import demo.config.GuestBookProfiles;
import demo.dto.CommentEntry;
import demo.test.dto.CommentEntryBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@ActiveProfiles(profiles = GuestBookProfiles.H2_INTEGRATION_TESTING)
public class CorsTests {
    private static final Long ID = 1L;
    private static final Short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final String COMMENTS_API_URL = "/api/comments/";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String ORIGIN_URL = "another.origin.org";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected MockHttpServletRequest request;

    @Autowired
    private CommentsApi commentsApi;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac).build();

        final CommentEntry commentEntry = this.buildAnonCommentEntry();

        when(this.commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));

    }


    @Test
    public void Getting_a_list_of_comments_should_return_200()
            throws Exception {
        this.mockMvc.perform(get(COMMENTS_API_URL)
                .header(ORIGIN_HEADER, ORIGIN_URL))
            .andExpect(status().is4xxClientError());
    }


    private CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .id(ID).created(CREATED).version(VERSION)
                .anonName(NAME).message(MESSAGE).username(null)
                .build();
    }


    @TestConfiguration
    public static class Config {
        @Bean
        public CommentsApi commentsApi() {
            return mock(CommentsApi.class);
        }
    }
}
