package com.example.truemart.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role_name;

    @ManyToMany(mappedBy = "roles")
    private List<UserTrueMart> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String role_name) {
        this.users = new ArrayList<>();
        this.role_name = role_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<UserTrueMart> getUsers() {
        return users;
    }

    public void setUsers(List<UserTrueMart> users) {
        this.users = users;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
