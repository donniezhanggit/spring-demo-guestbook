package demo.test.it.endpoints;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import demo.api.CommentsApi;
import demo.dto.CommentEntry;
import demo.test.dto.CommentEntryBuilder;
import demo.test.it.common.BaseEndpointCase;


public class CorsTests extends BaseEndpointCase {
    private static final Long ID = 1L;
    private static final Short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final String COMMENTS_API_URL = "/api/comments/";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String WRONG_ORIGIN_URL = "http://malicious.net";
    private static final String RIGHT_ORIGIN_URL = "http://localhost:8080";

    @Autowired
    private CommentsApi commentsApi;


    @Override
    public void setup() {
        final CommentEntry commentEntry = this.buildAnonCommentEntry();

        when(this.commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));
    }


    @Test
    public void Request_with_wrong_origin_should_return_403()
            throws Exception {
        mockMvc.perform(get(COMMENTS_API_URL)
                .header(ORIGIN_HEADER, WRONG_ORIGIN_URL))
            .andExpect(status().isForbidden());
    }


    @Test
    public void Request_with_right_origin_should_return_200()
            throws Exception {
        this.mockMvc.perform(get(COMMENTS_API_URL)
                .header(ORIGIN_HEADER, RIGHT_ORIGIN_URL))
            .andExpect(status().isOk());
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
