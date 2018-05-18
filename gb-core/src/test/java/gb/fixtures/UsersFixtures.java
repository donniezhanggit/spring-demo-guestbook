package gb.fixtures;

import gb.model.User;
import gb.model.UserBuilder;


public class UsersFixtures {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "P4ssW0rD";
    public static final String EMAIL = "user@mail.org";


    public static User buildUser() {
        return new UserBuilder()
                .username(USERNAME).password(PASSWORD).email(EMAIL)
                .active(true).build();
    }
}
