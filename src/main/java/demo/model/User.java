package demo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;


@Entity
@Table(name="gbuser")
public class User extends DomainEntity {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	@Email
	private String email;

	@NotNull
	private LocalDateTime created = LocalDateTime.now(); 

	@NotNull
	boolean active = true;

	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="user")
	List<Comment> comments;

	
	protected User() {}
	
	
	public User(@Valid @NotNull UserBuilder ub) {
		this.username = ub.username;
		this.password = ub.password;
		this.email    = ub.email;
		this.created  = ub.created;
		this.active   = ub.active;
		this.comments = ub.comments;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(@Nullable List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(@Valid @NotNull Comment comment) {
		this.comments.add(comment);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", email=" + email + ", created=" + created
				+ ", active=" + active + "]";
	}
}
