package demo.test.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.api.CommentsApi;
import demo.config.GuestBookProfiles;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
import demo.test.dto.CommentEntryBuilder;
import demo.test.dto.CommentInputBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@ActiveProfiles(profiles = GuestBookProfiles.H2_INTEGRATION_TESTING)
@EnableAutoConfiguration(exclude={
        LiquibaseAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class CommentsControllerTests {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass().getName());

    private static final Long ID = 1L;
    private static final Short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final Long NOT_EXISTING_ID = 2L;
    private static final String COMMENTS_API_URL = "/api/comments/";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    protected MockHttpServletRequest request;

    @Autowired
    private CommentsApi commentsApi;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac).build();

        final CommentEntry commentEntry = this.buildAnonCommentEntry();

        when(this.commentsApi.createComment(any(CommentInput.class)))
            .thenReturn(commentEntry);
        when(this.commentsApi.getComments())
            .thenReturn(Arrays.asList(commentEntry));
        when(this.commentsApi.getComment(ID))
            .thenReturn(Optional.of(commentEntry));
        when(this.commentsApi.getComment(NOT_EXISTING_ID))
            .thenReturn(Optional.empty());
    }


    @Test
    public void Getting_a_list_of_comments_should_return_200()
            throws Exception {
        this.mockMvc.perform(get(COMMENTS_API_URL))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void Getting_an_unexisted_comment_should_return_4xx()
            throws Exception {
        final String url = COMMENTS_API_URL + NOT_EXISTING_ID;

        this.mockMvc.perform(get(url))
            .andExpect(status().is4xxClientError());
    }


    @Test
    public void Creating_a_new_comment_should_return_200() throws Exception {
        final String jsonComment = this.objectMapper
                .writeValueAsString(this.buildAnonCommentInput());

        logger.info("CommentInput JSON: " + jsonComment);

        this.mockMvc.perform(post(COMMENTS_API_URL)
                .content(jsonComment)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    private CommentEntry buildAnonCommentEntry() {
        return new CommentEntryBuilder()
                .id(ID).created(CREATED).version(VERSION)
                .anonName(NAME).message(MESSAGE).username(null)
                .build();
    }


    private CommentInput buildAnonCommentInput() {
        return new CommentInputBuilder()
                .name(NAME).message(MESSAGE).build();
    }


    @TestConfiguration
    public static class Config {
        @Bean
        public CommentsApi commentsApi() {
            return mock(CommentsApi.class);
        }


//        @Bean
//        @Primary
//        public LiquibaseProperties liquibaseProperties() {
//            final LiquibaseProperties properties = new LiquibaseProperties();
//
//            properties.setEnabled(false);
//
//            return properties;
//        }
    }
}
