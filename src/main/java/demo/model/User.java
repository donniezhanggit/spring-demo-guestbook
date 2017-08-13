package demo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;


@Entity
@Table(name="gbuser")
public class User extends DomainEntity {
    private static final long serialVersionUID = 1L;

    public static final String USERNAME_PROPERTY = "username";
    public static final String PASSWORD_PROPERTY = "password";
    public static final String EMAIL_PROPERTY = "email";
    public static final String CREATED_PROPERTY = "created";
    public static final String ACTIVE_PROPERTY = "active";
    public static final String COMMENTS_PROPERTY = "comments";

    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 40;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final int EMAIL_MIN_LENGTH = 4;
    public static final int EMAIL_MAX_LENGTH = 40;

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

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="gbuser_id")
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
