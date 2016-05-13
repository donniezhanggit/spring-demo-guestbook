package demo.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import demo.forms.CommentForm;


@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Date created = new Date();

	@NotNull
	private String name;

	@NotNull
	private String message;


	public Comment() {
	}

	public Comment(CommentForm cf) {
		this.name = cf.getName();
		this.message = cf.getMessage();
	}

	public Long getId() {
		return id;
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
