package gb.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import gb.dto.CommentEntry;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE)
public class CommentEntryBuilder {
    Long id;
    Short version;
    LocalDateTime created;
    String anonName;
    String message;
    Long userId;
    String userName;


    public CommentEntryBuilder id(Long id) {
        this.id = id;

        return this;
    }


    public CommentEntryBuilder version(Short version) {
        this.version = version;

        return this;
    }


    public CommentEntryBuilder created(LocalDateTime created) {
        this.created = created;

        return this;
    }


    public CommentEntryBuilder anonName(String anonName) {
        this.anonName = anonName;

        return this;
    }


    public CommentEntryBuilder message(String message) {
        this.message = message;

        return this;
    }


    public CommentEntryBuilder userId(Long userId) {
        this.userId = userId;

        return this;
    }


    public CommentEntryBuilder userName(String userName) {
        this.userName = userName;

        return this;
    }


    public CommentEntry build() {
        return new CommentEntry(id, version, created, anonName,
                message, new SimpleUserEntry(userId, userName));
    }
}
