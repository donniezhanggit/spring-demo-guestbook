package gb.testlang.fixtures;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gb.model.User;
import gb.model.UserBuilder;
import gb.repos.UsersRepository;
import lombok.experimental.FieldDefaults;


@Component
@FieldDefaults(level=PRIVATE)
public class UsersFixtures {
    public static final Long EXISTING_ID = 10053L;
    public static final Short JUST_CREATED_VERSION = 0;
    public static final String USERNAME = "testUser";
    public static final String PASSWORD = "P4ssW0rD";
    public static final String EMAIL = "user@mail.org";


    @Autowired(required=false)
    UsersRepository usersRepo;


    public void prepareUser() {
        existingUser();
    }


    public User existingUser() {
        return usersRepo.findByUsername(USERNAME).orElse(createUser());
    }


    public User createUser() {
        final User newUser = buildUser();

        return usersRepo.save(newUser);
    }


    public static UserBuilder getFilledUserBuilder() {
        return new UserBuilder()
                .username(USERNAME).password(PASSWORD)
                .email(EMAIL).active(true);
    }


    public static User buildUser() {
        return getFilledUserBuilder().build();
    }


    public static User buildUserWithId() {
        final User user = buildUser();

        DomainClassFixtures.setId(user, EXISTING_ID);
        DomainClassFixtures.setVersion(user, JUST_CREATED_VERSION);

        return user;
    }


    public static User stubUser() {
        final User stubbedUser = mock(User.class);

        when(stubbedUser.getId()).thenReturn(EXISTING_ID);
        when(stubbedUser.getUsername()).thenReturn(USERNAME);

        return stubbedUser;
    }
}
