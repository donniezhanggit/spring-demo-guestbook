package gb.test.it.endpoints;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import gb.api.CommentsApi;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import gb.test.dto.CommentEntryBuilder;
import gb.test.dto.CommentInputBuilder;
import gb.test.it.common.EndpointITCase;


public class MainControllerTests extends EndpointITCase {
    private static final Long ID = 1L;
    private static final Short VERSION = 0;
    private static final String NAME = "anon";
    private static final String MESSAGE = "message";
    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final String ROOT_URL = "/";
    private static final String HTML_UTF8 = "text/html;charset=UTF-8";


    @Autowired
    private CommentsApi commentsApi;


    @Override
    public void setup() {
        final CommentEntry commentEntry = this.buildAnonCommentEntry();

//        when(this.commentsApi.createComment(any(CommentInput.class)))
//            .thenReturn(ID);
//        when(this.commentsApi.getComments())
//            .thenReturn(Arrays.asList(commentEntry));
    }


    @Test
    public void Getting_a_list_of_comments()
            throws Exception {
//        this.mockMvc.perform(get(ROOT_URL))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(HTML_UTF8));
    }


//    @Test
//    public void Creating_a_new_comment() throws Exception {
//        final String jsonComment = this.jsonify(this.buildAnonCommentInput());
//
//        this.mockMvc.perform(post(ROOT_URL)
//                .content(jsonComment)
//                .contentType(MediaType.TEXT_PLAIN))
//            .andExpect(status().is2xxSuccessful());
//    }


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
    }
}
