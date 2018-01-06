package gb.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import gb.common.domain.DomainEntity;


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
    @Length(min=USERNAME_MIN_LENGTH, max=USERNAME_MAX_LENGTH)
    private String username;

    @NotNull
    @Length(min=PASSWORD_MIN_LENGTH, max=PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    @Email
    @Length(min=EMAIL_MIN_LENGTH, max=EMAIL_MAX_LENGTH)
    private String email;

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    boolean active = true;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="gbuser_id")
    List<Comment> comments = new ArrayList<>();


    protected User() {}


    public User(@Valid @Nonnull UserBuilder ub) {
        this.username = ub.username;
        this.password = ub.password;
        this.email    = ub.email;
        this.created  = ub.created;
        this.active   = ub.active;
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

    public void addComment(@Nonnull Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(@Nonnull Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
    }
}
