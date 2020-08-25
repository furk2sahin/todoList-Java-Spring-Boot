package com.furkansahin.todolist.service;

import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.exception.TodoNotFoundException;
import com.furkansahin.todolist.model.TodoItem;
import com.furkansahin.todolist.model.TodoList;

import java.util.List;

public interface TodoListService {
    List<TodoList> findLists();
    List<TodoList> findListsByNameLike(String keyword);
    TodoList getListById(Long id) throws ListNotFoundException;
    TodoList getListByTodoId(Long id) throws ListNotFoundException;
    List<TodoItem> findTodoItems();
    TodoItem getTodoItemById(Long id) throws TodoNotFoundException;
    void createList(TodoList list);
    void updateList(TodoList list);
    void deleteList(Long id);
    void createTodo(TodoItem item);
    void updateTodo(TodoItem item);
    void deleteTodo(Long id);
}
