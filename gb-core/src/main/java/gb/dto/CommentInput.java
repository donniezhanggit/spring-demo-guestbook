package gb.dto;

import static gb.model.Comment.ANON_NAME_MAX_LENGTH;
import static gb.model.Comment.ANON_NAME_MIN_LENGTH;
import static gb.model.Comment.MESSAGE_MAX_LENGTH;
import static gb.model.Comment.MESSAGE_MIN_LENGTH;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;


@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
@FieldNameConstants
public class CommentInput {
    @Length(min=ANON_NAME_MIN_LENGTH, max=ANON_NAME_MAX_LENGTH)
    String anonName;

    @NotNull
    @Length(min=MESSAGE_MIN_LENGTH, max=MESSAGE_MAX_LENGTH)
    String message;


    public static CommentInput empty() {
        return new CommentInput(null, null);
    }
}
