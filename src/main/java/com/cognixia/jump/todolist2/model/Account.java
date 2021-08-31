package com.cognixia.jump.todolist2.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String username;

    @Size(min = 5)
    @Pattern(regexp = "^[A-Za-z0-9]+[@#!$&*]+[A-Za-z0-9@#!$&*]*")
    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<ToDo> toDoList;

    public Account(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.toDoList = new ArrayList<>();
    }

    public Account(){
        this(-1, "admin", "password@");
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setToDoList(List<ToDo> toDoList) {
        this.toDoList = toDoList;
    }


    public List<ToDo> getToDoList() {
        return toDoList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", toDoList=" + toDoList +
                '}';
    }
}
