package demo.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class CommentInput {
    @Length(min=1, max=20)
    private String name;

    @Length(min=1, max=2048)
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
