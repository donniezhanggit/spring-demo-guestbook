package gb.endpoints;

import static gb.testlang.fixtures.FullNameFixtures.buildFullNameInput;
import static gb.testlang.fixtures.UsersFixtures.EXISTING_USERNAME;
import static gb.testlang.fixtures.UsersFixtures.NON_EXISTENT_USERNAME;
import static gb.testlang.fixtures.UsersFixtures.buildUserEntry;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import gb.api.UsersApi;
import gb.common.it.EndpointITCase;
import gb.controllers.UsersController;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
@WebMvcTest(UsersController.class)
@WithMockUser(username=EXISTING_USERNAME, roles={"USER", "ADMIN", "ACTUATOR"})
public class UsersControllerTests extends EndpointITCase {
    private static final String USERS_API_URL = "/api/users/";


    @MockBean
    UsersApi usersApi;


    @Override
    protected void setup() {
        final UserEntry userEntry = buildUserEntry();

        when(usersApi.getUser(EXISTING_USERNAME))
            .thenReturn(Optional.ofNullable(userEntry));
        when(usersApi.getUser(NON_EXISTENT_USERNAME))
            .thenReturn(Optional.empty());
        doNothing().when(usersApi).activateUser(anyString());
        doNothing().when(usersApi).deactivateUser(anyString());
        doNothing().when(usersApi)
            .changeName(anyString(), any(FullNameInput.class));
    }


    @Test
    public void Getting_an_existing_user_should_return_200() throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME;

        // Act and assert.
        mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }


    @Test
    public void Getting_an_existing_user_should_call_APIs_getUser()
            throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME;

        // Act.
        mockMvc.perform(get(url));

        // Assert.
        verify(usersApi, times(1)).getUser(EXISTING_USERNAME);
        verifyNoMoreInteractions(usersApi);
    }


    @Test
    public void Getting_a_non_existent_user_should_return_404()
            throws Exception {
        // Arrange.
        final String url = USERS_API_URL + NON_EXISTENT_USERNAME;

        // Act and assert.
        mockMvc.perform(get(url))
            .andExpect(status().isNotFound());
    }


    @Test
    public void Getting_a_non_existent_user_should_call_APIs_getUser()
            throws Exception {
        // Arrange.
        final String url = USERS_API_URL + NON_EXISTENT_USERNAME;

        // Act.
        mockMvc.perform(get(url));

        // Assert.
        verify(usersApi, times(1)).getUser(NON_EXISTENT_USERNAME);
        verifyNoMoreInteractions(usersApi);
    }


    @Test
    public void Deactivating_of_user_should_return_204() throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME + "/blocked";

        // Act and assert.
        mockMvc.perform(put(url))
            .andExpect(status().isNoContent());
    }


    @Test
    public void Deactivating_of_user_should_call_APIs_deactivateUser()
            throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME + "/blocked";

        // Act.
        mockMvc.perform(put(url));

        // Assert.
        verify(usersApi, times(1)).deactivateUser(EXISTING_USERNAME);
    }


    @Test
    public void Activating_of_user_should_return_204() throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME + "/blocked";

        // Act and assert.
        mockMvc.perform(delete(url))
            .andExpect(status().isNoContent());
    }


    @Test
    public void Activating_of_user_should_call_APIs_deactivateUser()
            throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME + "/blocked";

        // Act.
        mockMvc.perform(delete(url));

        // Assert.
        verify(usersApi, times(1)).activateUser(EXISTING_USERNAME);
    }


    @Test
    public void Changing_fullName_should_return_204() throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME + "/fullName";
        final FullNameInput input = buildFullNameInput();
        final String inputAsJson = jsonify(input);

        // Act and assert.
        mockMvc.perform(put(url)
                .content(inputAsJson)
                .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());
    }


    @Test
    public void Changing_fullName_should_call_APIs_changeName()
            throws Exception {
        // Arrange.
        final String url = USERS_API_URL + EXISTING_USERNAME + "/fullName";
        final FullNameInput input = buildFullNameInput();
        final String inputAsJson = jsonify(input);

        // Act.
        mockMvc.perform(put(url)
                .content(inputAsJson)
                .contentType(APPLICATION_JSON_UTF8));

        // Assert.
        verify(usersApi, times(1)).changeName(EXISTING_USERNAME, input);
    }
}
