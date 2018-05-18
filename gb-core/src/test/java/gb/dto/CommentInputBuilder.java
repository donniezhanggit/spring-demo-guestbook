package gb.dto;

import static lombok.AccessLevel.PACKAGE;

import javax.annotation.Nullable;

import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PACKAGE)
public class CommentInputBuilder {
    String name;
    String message;


    public CommentInputBuilder name(@Nullable String name) {
        this.name = name;

        return this;
    }


    public CommentInputBuilder message(@Nullable String message) {
        this.message = message;

        return this;
    }


    public CommentInput build() {
        return new CommentInput(name, message);
    }
}
