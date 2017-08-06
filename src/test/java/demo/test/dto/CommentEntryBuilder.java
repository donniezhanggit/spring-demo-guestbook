package demo.test.dto;

import java.time.LocalDateTime;

import demo.dto.CommentEntry;


public class CommentEntryBuilder {
    private CommentEntry entry = new CommentEntry();


    public CommentEntryBuilder id(Long id) {
        this.entry.setId(id);
        return this;
    }


    public CommentEntryBuilder version(Short version) {
        this.entry.setVersion(version);
        return this;
    }


    public CommentEntryBuilder created(LocalDateTime created) {
        this.entry.setCreated(created);
        return this;
    }


    public CommentEntryBuilder anonName(String anonName) {
        this.entry.setAnonName(anonName);
        return this;
    }


    public CommentEntryBuilder message(String message) {
        this.entry.setMessage(message);
        return this;
    }


    public CommentEntryBuilder username(String username) {
        this.entry.setUsername(username);
        return this;
    }


    public CommentEntry build() {
        return this.entry;
    }
}
