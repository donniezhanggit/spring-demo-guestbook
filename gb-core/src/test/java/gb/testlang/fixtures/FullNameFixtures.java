package gb.testlang.fixtures;

import gb.dto.FullNameInput;
import gb.dto.FullNameInputBuilder;
import gb.model.FullName;
import lombok.experimental.UtilityClass;


@UtilityClass
public class FullNameFixtures {
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Dow";


    public FullNameInputBuilder filledFullNameBuilder() {
        return new FullNameInputBuilder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME);
    }


    public FullNameInput buildFullNameInput() {
        return filledFullNameBuilder().build();
    }


    public FullName buildFullName() {
        return new FullName(FIRST_NAME, LAST_NAME);
    }
}
