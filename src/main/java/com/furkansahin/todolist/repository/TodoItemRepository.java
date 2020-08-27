package com.furkansahin.todolist.repository;

import com.furkansahin.todolist.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}