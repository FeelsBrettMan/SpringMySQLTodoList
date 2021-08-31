package com.cognixia.jump.todolist2.model;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class ToDo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String description;


    private LocalDate dueDate;
    
    private boolean isDone;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    public ToDo(int id, String description, boolean isDone, LocalDate dueDate, Account account) {
        this.id = id;
        this.description = description;
        this.isDone = isDone;
        this.dueDate = dueDate;
        this.account = account;
        
    }

    public ToDo(){
        this(-1, "placeholder", false, LocalDate.now(), new Account());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", isDone=" + isDone +
                ", account=" + account.getId() +
                '}';
    }
}
