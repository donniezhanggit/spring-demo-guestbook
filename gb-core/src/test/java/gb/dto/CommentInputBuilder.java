package gb.dto;

import javax.validation.constraints.NotNull;

import gb.dto.CommentInput;


public class CommentInputBuilder {
    private String name;
    private String message;


    public CommentInputBuilder name(@NotNull String name) {
        this.name = name;
        return this;
    }


    public CommentInputBuilder message(@NotNull String message) {
        this.message = message;
        return this;
    }


    public CommentInput build() {
        return new CommentInput(name, message);
    }
}
