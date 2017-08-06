package demo.test.dto;

import java.time.LocalDateTime;

import demo.dto.CommentEntry;


public class CommentEntryBuilder {
    private CommentEntry entry = new CommentEntry();


    public CommentEntryBuilder id(Long id) {
        entry.setId(id);
        return this;
    }


    public CommentEntryBuilder created(LocalDateTime created) {
        entry.setCreated(created);
        return this;
    }


    public CommentEntryBuilder anonName(String anonName) {
        entry.setAnonName(anonName);
        return this;
    }


    public CommentEntryBuilder message(String message) {
        entry.setMessage(message);
        return this;
    }


    public CommentEntryBuilder username(String username) {
        entry.setUsername(username);
        return this;
    }


    public CommentEntry build() {
        return this.entry;
    }
}