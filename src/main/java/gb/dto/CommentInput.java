package gb.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import gb.model.Comment;


public class CommentInput {
    @Length(min=Comment.NAME_MIN_LENGTH, max=Comment.NAME_MAX_LENGTH)
    private String name;

    @Length(min=Comment.MESSAGE_MIN_LENGTH, max=Comment.MESSAGE_MAX_LENGTH)
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @NotNull
    @Override
    public String toString() {
        return "CommentInput [name=" + name + ", message=" + message + "]";
    }
}
