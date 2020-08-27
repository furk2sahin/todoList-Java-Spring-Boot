package com.furkansahin.todolist.service;

import com.furkansahin.todolist.exception.TodoNotFoundException;
import com.furkansahin.todolist.model.TodoItem;

import java.util.List;

public interface TodoItemService {
    List<TodoItem> findTodoItems();
    TodoItem getTodoItemById(Long id) throws TodoNotFoundException;
    TodoItem createTodo(TodoItem item);
    void updateTodo(Long id, String item);
    void deleteTodo(Long id);
}
