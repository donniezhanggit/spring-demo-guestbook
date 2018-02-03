package gb.test.dto;

import javax.validation.constraints.NotNull;

import gb.dto.CommentInput;


public class CommentInputBuilder {
    private CommentInput input = new CommentInput();


    public CommentInputBuilder name(@NotNull String name) {
        this.input.setName(name);
        return this;
    }


    public CommentInputBuilder message(@NotNull String message) {
        this.input.setMessage(message);
        return this;
    }


    public CommentInput build() {
        return this.input;
    }
}
