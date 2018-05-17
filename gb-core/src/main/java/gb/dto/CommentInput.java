package gb.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import gb.model.Comment;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CommentInput {
    @NotNull
    @Length(min=Comment.NAME_MIN_LENGTH, max=Comment.NAME_MAX_LENGTH)
    String name;

    @NotNull
    @Length(min=Comment.MESSAGE_MIN_LENGTH, max=Comment.MESSAGE_MAX_LENGTH)
    String message;


    public static CommentInput empty() {
        return new CommentInput(null, null);
    }
}
