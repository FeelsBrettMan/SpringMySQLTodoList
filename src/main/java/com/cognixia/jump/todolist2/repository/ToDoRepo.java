package com.cognixia.jump.todolist2.repository;

import com.cognixia.jump.todolist2.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepo extends JpaRepository<ToDo, Integer> {
}

