package com.furkansahin.todolist.repository;

import com.furkansahin.todolist.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByTitleContaining(String title);
}