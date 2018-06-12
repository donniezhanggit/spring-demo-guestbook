package gb.dto;

import static gb.model.FullName.FIRST_NAME_MAX_LENGTH;
import static gb.model.FullName.FIRST_NAME_MIN_LENGTH;
import static gb.model.FullName.LAST_NAME_MAX_LENGTH;
import static gb.model.FullName.LAST_NAME_MIN_LENGTH;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Value;
import lombok.experimental.FieldNameConstants;


@Value
@FieldNameConstants
public class FullNameInput {
    @NotEmpty
    @Length(min=FIRST_NAME_MIN_LENGTH, max=FIRST_NAME_MAX_LENGTH)
    String firstName;

    @NotEmpty
    @Length(min=LAST_NAME_MIN_LENGTH, max=LAST_NAME_MAX_LENGTH)
    String lastName;
}
