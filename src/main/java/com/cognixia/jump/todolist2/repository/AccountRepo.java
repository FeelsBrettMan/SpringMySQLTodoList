package com.cognixia.jump.todolist2.repository;

import com.cognixia.jump.todolist2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {

    Optional<Account> findByUsernameAndPassword(String username, String password);
}
