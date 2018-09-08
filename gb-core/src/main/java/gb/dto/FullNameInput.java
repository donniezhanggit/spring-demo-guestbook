package gb.dto;

import static gb.model.FullName.FIRST_NAME_MAX_LENGTH;
import static gb.model.FullName.FIRST_NAME_MIN_LENGTH;
import static gb.model.FullName.LAST_NAME_MAX_LENGTH;
import static gb.model.FullName.LAST_NAME_MIN_LENGTH;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import gb.model.FullName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;


@Value
@Builder
@AllArgsConstructor
@FieldNameConstants
public class FullNameInput {
    @NotEmpty
    @Length(min=FIRST_NAME_MIN_LENGTH, max=FIRST_NAME_MAX_LENGTH)
    String firstName;

    @NotEmpty
    @Length(min=LAST_NAME_MIN_LENGTH, max=LAST_NAME_MAX_LENGTH)
    String lastName;


    public FullName toFullName() {
        return new FullName(firstName, lastName);
    }
}
