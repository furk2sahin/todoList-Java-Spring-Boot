package com.furkansahin.todolist.service;

import com.furkansahin.todolist.repository.TodoItemRepository;
import com.furkansahin.todolist.repository.TodoListRepository;
import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService{

    private TodoListRepository todoListRepository;

    private TodoItemRepository todoItemRepository;

    @Autowired
    public void setTodoItemRepository(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Autowired
    public void setTodoListRepository(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Override
    public List<TodoList> findLists() {
        return todoListRepository.findAll();
    }

    @Override
    public List<TodoList> findListsByNameLike(String keyword) {
        return todoListRepository.findByTitleContaining(keyword);
    }

    @Override
    public TodoList getListById(Long id) throws ListNotFoundException {
        return todoListRepository.findById(id).orElseThrow(()->{return new ListNotFoundException("List Not found by id: " + id);});
    }

    @Override
    public void createList(TodoList list) {
        todoListRepository.save(list);
    }

    @Override
    public void updateList(Long id, String title) {
        TodoList listToUpdate = todoListRepository.getOne(id);
        listToUpdate.setTitle(title);
        todoListRepository.save(listToUpdate);
    }

    @Override
    public void deleteList(Long id) {
        todoListRepository.deleteById(id);
    }
}
