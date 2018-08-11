package gb.endpoints;

import static gb.testlang.fixtures.FullNameFixtures.buildFullNameInput;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static gb.testlang.fixtures.UsersFixtures.buildUserEntry;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.UserApi;
import gb.common.it.EndpointITCase;
import gb.controllers.UserController;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WebMvcTest(UserController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class UserControllerTests extends EndpointITCase {
    private static final String CURRENT_USER_API_URL = "/api/user";


    @MockBean
    UserApi userApi;


    @Override
    protected void setup() {
        final UserEntry userEntry = buildUserEntry();

        when(userApi.getCurrentUser())
            .thenReturn(userEntry);
        doNothing().when(userApi)
            .changeNameOfCurrentUser(any(FullNameInput.class));
    }


    @Test
    public void Getting_current_user_should_return_200() throws Exception {
        mockMvc.perform(get(CURRENT_USER_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }


    @Test
    public void 
    Getting_current_user_should_call_APIs_getCurrentUser() throws Exception {
        // Act.
        mockMvc.perform(get(CURRENT_USER_API_URL));

        // Assert.
        verify(userApi, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userApi);
    }


    @Test
    public void 
    Changing_name_of_current_user_should_return_204() throws Exception {
        // Arrange.
        final FullNameInput input = buildFullNameInput();
        final String inputAsJson = jsonify(input);

        // Act and assert.
        mockMvc.perform(put(CURRENT_USER_API_URL)
                .content(inputAsJson)
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());
    }


    @Test
    public void 
    Changing_name_of_current_user_should_APIs_changeNameOfCurrentUser()
            throws Exception {
        // Arrange.
        final FullNameInput input = buildFullNameInput();
        final String inputAsJson = jsonify(input);

        // Act.
        mockMvc.perform(put(CURRENT_USER_API_URL)
                .content(inputAsJson)
                .contentType(APPLICATION_JSON_UTF8));

        // Assert.
        verify(userApi, times(1)).changeNameOfCurrentUser(input);
        verifyNoMoreInteractions(userApi);
    }
}
