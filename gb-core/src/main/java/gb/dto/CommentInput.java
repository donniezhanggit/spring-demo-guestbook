package gb.dto;

import static gb.model.Comment.MESSAGE_MAX_LENGTH;
import static gb.model.Comment.MESSAGE_MIN_LENGTH;
import static gb.model.Comment.NAME_MAX_LENGTH;
import static gb.model.Comment.NAME_MIN_LENGTH;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CommentInput {
    @NotNull
    @Length(min=NAME_MIN_LENGTH, max=NAME_MAX_LENGTH)
    String name;

    @NotNull
    @Length(min=MESSAGE_MIN_LENGTH, max=MESSAGE_MAX_LENGTH)
    String message;


    public static CommentInput empty() {
        return new CommentInput(null, null);
    }
}
