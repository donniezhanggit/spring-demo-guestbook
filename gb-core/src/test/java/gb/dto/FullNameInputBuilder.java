package gb.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
public class FullNameInputBuilder {
    String firstName;
    String lastName;


    public FullNameInputBuilder firstName(final String firstName) {
        this.firstName = firstName;

        return this;
    }


    public FullNameInputBuilder lastName(final String lastName) {
        this.lastName = lastName;

        return this;
    }


    public FullNameInput build() {
        return new FullNameInput(firstName, lastName);
    }
}
