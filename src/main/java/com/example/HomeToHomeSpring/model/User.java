package com.example.HomeToHomeSpring.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@ToString
@RequiredArgsConstructor
public class User {


    @Id
    private String userName;
    private String userFullName;

    private String password;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    private boolean credentialsNonExpired;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userName != null && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
