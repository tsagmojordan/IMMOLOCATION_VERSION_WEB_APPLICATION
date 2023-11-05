package com.example.immolocation.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users")
public  class User implements Serializable {

// @Column(unique = true )
@Id
private String login;
private String mot_de_passe;
private Long Id;


 @ManyToMany(fetch = FetchType.EAGER)
 @JoinTable(name="User_Role",
            joinColumns= @JoinColumn(name ="users_login"),
            inverseJoinColumns =@JoinColumn(name="roles_role")
            )
 private List<Role> roles = new ArrayList<>();

    public void addRole(Role role)
    {
        this.roles.add(role);

    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
