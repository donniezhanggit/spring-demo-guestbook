package demo.dto;


import java.time.LocalDateTime;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import demo.model.Comment;


public class CommentEntry {
	private Long id;
	private Short version;
	private LocalDateTime created;
	private String anonName;
	private String message;
	private String username;
	
	
	public Long getId() {
		return id;
	}
	
	public Short getVersion() {
		return version;
	}
	
	public LocalDateTime getCreated() {
		return created;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getAnonName() {
		return anonName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setVersion(Short version) {
		this.version = version;
	}
	
	public void setCreated(LocalDateTime date) {
		this.created = date;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setAnonName(String name) {
		this.anonName = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@NotNull
	@Override
	public String toString() {
		return "CommentEntry [created=" + created + ", name=" + anonName
				+ ", message=" + message + ", username=" + username + "]";
	}

	
	@Nullable
	public static CommentEntry from(@Nullable final Comment comment) {
		if(null == comment) {
			return null;
		}
		
		final CommentEntry entry = new CommentEntry();
		
		entry.setId(comment.getId());
		entry.setVersion(comment.getVersion());
		entry.setCreated(comment.getCreated());
		entry.setMessage(comment.getMessage());
		entry.setAnonName(comment.getName());
		
		if(null != comment.getUser()) {
			entry.setUsername(comment.getUser().getUsername());
		}
		
		return entry;
	}
}
