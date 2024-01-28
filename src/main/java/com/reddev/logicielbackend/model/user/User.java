package com.reddev.logicielbackend.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reddev.logicielbackend.model.student.StudentCourse;
import com.reddev.logicielbackend.model.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(
        name = "users"
)
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
    @JsonIgnore
    private LocalDateTime lastLogin;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<StudentCourse> courses;


    private Boolean locked = false;
    private Boolean enabled = false;

    public User(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.type);
        return Collections.singletonList(authority);

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
