package gb.dto;

import static gb.model.FullName.FIRST_NAME_MAX_LENGTH;
import static gb.model.FullName.FIRST_NAME_MIN_LENGTH;
import static gb.model.FullName.LAST_NAME_MAX_LENGTH;
import static gb.model.FullName.LAST_NAME_MIN_LENGTH;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Value;


@Value
public class FullNameInput {
    @NotNull
    @Length(min=FIRST_NAME_MIN_LENGTH, max=FIRST_NAME_MAX_LENGTH)
    String firstName;

    @NotNull
    @Length(min=LAST_NAME_MIN_LENGTH, max=LAST_NAME_MAX_LENGTH)
    String lastName;
}