package com.cognixia.jump.todolist2.controller;

import com.cognixia.jump.todolist2.model.Account;
import com.cognixia.jump.todolist2.model.ToDo;
import com.cognixia.jump.todolist2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService service;

    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/account")
    public ResponseEntity<?> getAccount(@RequestBody Account account){
        Account found = service.getAccount(account);
        if(found.getId()==-1) return ResponseEntity.status(404).body("Not Found");
        return ResponseEntity.status(200).body(found);
    }

    @GetMapping("/customAccount")
    public ResponseEntity<?> getAccountCustomQuery(@RequestBody Account account){
        Account found = service.getAccountCustomQuery(account);
        if(found.getId()==-1) return ResponseEntity.status(404).body("Not Found ");
        return ResponseEntity.status(200).body(found);
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> addAccount(@RequestBody Account account){
        Account added = service.addAccount(account);
        if(added.getId()==-1) return ResponseEntity.status(400).body("Username Invalid!");
        return ResponseEntity.status(201).body(added);
    }

    @PatchMapping("/account")
    public ResponseEntity<?> updateAccount(@RequestBody List<Account> accounts){
        Account current = accounts.get(0);
        Account updated = accounts.get(1);
        if(!current.getUsername().equals(updated.getUsername())){
            if(!current.getPassword().equals(updated.getPassword())){
                return ResponseEntity.status(400).body("Can only change one field at a time!");
            }
            Account patched = service.updateUsername(current, updated);
            if(patched.getId()==-1) return ResponseEntity.status(400).body("Username or password is incorrect!");
            return ResponseEntity.status(200).body(patched);
        }
        if(!current.getPassword().equals(updated.getPassword())){
            Account patched = service.updatePassword(current, updated);
            if(patched.getId()==-1) return ResponseEntity.status(400).body("Username or password is incorrect!");
            return ResponseEntity.status(200).body(patched);
        }
        return ResponseEntity.status(400).body("fields are unchanged!");
    }


    private static class AccountTodo{
        public Account account;
        public ToDo toDo;
    }

    @PutMapping("/account")
    public ResponseEntity<?> addTodo(@RequestBody  AccountTodo test){
        Account account = test.account;
        ToDo todo = test.toDo;
        Account added = service.addTodo(account,todo);
        if(added.getId() ==-1) return ResponseEntity.status(404).body("Account not found");
        return ResponseEntity.status(200).body(added);
    }

    private static class AccountInteger{
        public Account account;
        public Integer integer;
        public LocalDate date;
    }
    @PatchMapping("/account/todo")
    public  ResponseEntity<?> updateDone(@RequestBody AccountInteger test){
        Account account = test.account;
        Integer todoID = test.integer;
        LocalDate date = test.date;
        int success;
        if(date== null){
             success =  service.updateDone(account, todoID);

        }
        else {
            success = service.updateDate(account, todoID, date);
        }
        switch (success) {
            case 404: return ResponseEntity.status(404).body("account not found");
            case 405: return ResponseEntity.status(404).body("todo not found");
            case 1: return ResponseEntity.status(200).body(getAccountCustomQuery(account));
        }

        return ResponseEntity.status(400).body("error contact dev");

    }

    @DeleteMapping("/account/todo")
    public ResponseEntity<?> deleteTodo(@RequestBody AccountInteger test){
        Account account = test.account;
        Integer todoID = test.integer;
        int success = service.deleteTodo(account, todoID);
        switch (success) {
            case 404: return ResponseEntity.status(404).body("account not found");
            case 405: return ResponseEntity.status(404).body("todo not found");
            case 1: return ResponseEntity.status(200).body(getAccountCustomQuery(account));
        }
        return ResponseEntity.status(400).body("error contact dev");
    }

    @PatchMapping("/account/todo/all")
    public  ResponseEntity<?> updateAll(@RequestBody Account account){
        Account found = service.finishAll(account);
        if(found.getId()==-1) return ResponseEntity.status(404).body("account not found");
        return ResponseEntity.status(200).body(getAccountCustomQuery(account));
    }
    @DeleteMapping ("/account/todo/all")
    public  ResponseEntity<?> deleteAll(@RequestBody Account account){
        Account found = service.deleteAll(account);
        if(found.getId()==-1) return ResponseEntity.status(404).body("account not found");
        return ResponseEntity.status(200).body(getAccountCustomQuery(account));
    }



}
