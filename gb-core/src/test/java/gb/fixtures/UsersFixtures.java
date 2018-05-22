package gb.fixtures;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.test.util.ReflectionTestUtils;

import gb.model.User;
import gb.model.UserBuilder;


public class UsersFixtures {
    public static final Long EXISTING_ID = 1L;
    public static final Short JUST_CREATED_VERSION = 0;
    public static final String USERNAME = "user";
    public static final String PASSWORD = "P4ssW0rD";
    public static final String EMAIL = "user@mail.org";


    public static User buildUser() {
        return new UserBuilder()
                .username(USERNAME).password(PASSWORD).email(EMAIL)
                .active(true).build();
    }


    public static User buildUserWithId() {
        final User user = buildUser();

        ReflectionTestUtils.setField(user, "id", EXISTING_ID);
        ReflectionTestUtils.setField(user, "version", JUST_CREATED_VERSION);

        return user;
    }


    public static User stubUser() {
        final User stubbedUser = mock(User.class);

        when(stubbedUser.getId()).thenReturn(EXISTING_ID);
        when(stubbedUser.getUsername()).thenReturn(USERNAME);

        return stubbedUser;
    }
}
