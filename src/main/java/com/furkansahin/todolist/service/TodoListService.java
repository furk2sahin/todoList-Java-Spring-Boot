package com.furkansahin.todolist.service;

import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.model.TodoList;

import java.util.List;

public interface TodoListService {
    List<TodoList> findLists();
    List<TodoList> findListsByNameLike(String keyword);
    TodoList getListById(Long id) throws ListNotFoundException;
    void createList(TodoList list);
    void updateList(Long id, String title);
    void deleteList(Long id);

}
