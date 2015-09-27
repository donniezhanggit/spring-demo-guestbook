package models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import forms.CommentForm;

@Entity
@Table(name="comments")
public class Comment {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name="created")
	private Date created = new Date();

	@NotNull
	@Column(name="name")
	private String name;

	@NotNull
	@Column(name="message")
	private String message;

	public Comment() {
	}
	
	public Comment(CommentForm cf) {
		this.name = cf.getName();
		this.message = cf.getMessage();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date date) {
		this.created = date;
	}

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

	@Override
	public String toString() {
		return "Comment [id=" + id + ", created=" + created + ", name=" + name
				+ ", message=" + message + "]";
	}
}
