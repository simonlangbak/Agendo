package com.simonlangbak.agendo.domain.user;

import com.simonlangbak.agendo.domain.BaseEntity;
import com.simonlangbak.agendo.domain.todo.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity implements UserDetails, Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "assignees")
    private SortedSet<Task> tasks = new TreeSet<>();

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date created;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + '}';
    }

    @Override
    public int compareTo(User o) {
        return this.id == null || o.id == null ? 1 : this.id.compareTo(o.id);
    }
}
