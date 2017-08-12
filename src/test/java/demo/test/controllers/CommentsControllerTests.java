package demo.test.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import demo.config.GuestBookProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@ActiveProfiles(profiles = GuestBookProfiles.H2_INTEGRATION_TESTING)
public class CommentsControllerTests {
    @Autowired protected WebApplicationContext wac;
    @Autowired protected MockHttpServletRequest request;

    protected MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac).build();
    }


    @Test
    public void test() throws Exception {
        this.mockMvc.perform(get("/api/comments"))
            .andExpect(status().isOk());
    }

    @Test
    public void test2() throws Exception {
        this.mockMvc.perform(get("/api/comments/2"))
            .andExpect(status().is4xxClientError());
    }

}
