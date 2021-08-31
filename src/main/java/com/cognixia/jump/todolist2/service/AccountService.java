package com.cognixia.jump.todolist2.service;

import com.cognixia.jump.todolist2.model.Account;
import com.cognixia.jump.todolist2.model.ToDo;
import com.cognixia.jump.todolist2.repository.AccountRepo;
import com.cognixia.jump.todolist2.repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepo repo;
    @Autowired
    ToDoRepo toDoRepo;

    public List<Account> getAccounts(){
        return repo.findAll();
    }

    public Account getAccount(Account account){
        List<Account> allAccounts = getAccounts();
        Optional<Account> toGet = allAccounts.stream().filter(account1 -> account1.getUsername().equals(account.getUsername())).filter(account1 -> account1.getPassword().equals(account.getPassword())).findFirst();
        return toGet.orElseGet(Account::new);
    }

    public Account getAccountCustomQuery(Account account){
        Optional<Account> accounts = repo.findByUsernameAndPassword(account.getUsername(), account.getPassword());

        return accounts.orElseGet(Account::new);
    }

    public Account addAccount(Account account){
        Optional<Account> accountExist = getAccounts().stream().filter(account1 -> account1.getUsername().equals(account.getUsername())).findFirst();
        if(accountExist.isPresent()) return new Account();
        account.setId(-1);
        return repo.save(account);
    }

    public Account updatePassword(Account current, Account updated){
        Account found = getAccount(current);
        if(found.getId()==-1) return found;
        found.setPassword(updated.getPassword());
        return repo.save(found);
    }
    public Account updateUsername(Account current, Account updated){
        Account found = getAccount(current);
        if(found.getId()==-1) return found;
        found.setUsername(updated.getUsername());
        return repo.save(found);
    }

    public Account addTodo(Account account, ToDo todo){
        Account found = getAccount(account);
        if(found.getId()==-1) return found;
        todo.setId(-1);
        todo.setAccount(found);
        List<ToDo> currentList = found.getToDoList();
        currentList.add(todo);
        System.out.println(todo);
        found.setToDoList(currentList);

        return repo.save(found);
    }

    public int updateDone(Account account, Integer todoID){
        Account found = getAccount(account);
        if(found.getId()==-1) return 404;
        List<ToDo> list = found.getToDoList();
        Optional<ToDo> current = list.stream().filter(toDo -> toDo.getId()==todoID).findFirst();
        if(current.isPresent())  current.get().setDone(!current.get().isDone());
        else return 405;
        found.setToDoList(list);
        repo.save(found);
        return 1;
    }

    public int updateDate(Account account, Integer todoID, LocalDate date){
        Account found = getAccount(account);
        if(found.getId()==-1) return 404;
        List<ToDo> list = found.getToDoList();
        Optional<ToDo> current = list.stream().filter(toDo -> toDo.getId()==todoID).findFirst();
        if(current.isPresent())  current.get().setDueDate(date);
        else return 405;
        found.setToDoList(list);
        repo.save(found);
        return 1;
    }

    public int deleteTodo(Account account, Integer todoID){
        Account found = getAccount(account);
        if(found.getId()==-1) return 404;
        List<ToDo> list = found.getToDoList();
        Optional<ToDo> current = list.stream().filter(toDo -> toDo.getId()==todoID).findFirst();
        if(current.isPresent())   list.remove(current.get());
        else return 405;
        found.setToDoList(list);
        repo.save(found);
        return 1;
    }

    public Account finishAll(Account account){
        Account found = getAccount(account);
        if(found.getId()==-1) return new Account();
        List<ToDo> list = found.getToDoList();
        list.forEach(current -> current.setDone(true));
        found.setToDoList(list);
        repo.save(found);
        return found;
    }

    public Account deleteAll(Account account){
        Account found = getAccount(account);
        if(found.getId()==-1) return new Account();
        List<ToDo> list = found.getToDoList();
        List<Integer> ids = new ArrayList<>();
        list.forEach(current -> ids.add(current.getId()));
        for(int i=0; i< ids.size();i++){
            deleteTodo(found, ids.get(i));
        }
        found.setToDoList(list);
        repo.save(found);
        return found;
    }
}
