package gb.testlang.fixtures;

import gb.domain.FullName;
import gb.dto.FullNameInput;


public class FullNameFixtures {
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Dow";


    public static FullNameInput.FullNameInputBuilder
    filledFullNameInputBuilder() {
        return FullNameInput.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME);
    }


    public static FullNameInput buildFullNameInput() {
        return filledFullNameInputBuilder().build();
    }


    public static FullName buildFullName() {
        return new FullName(FIRST_NAME, LAST_NAME);
    }
}
