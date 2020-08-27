package com.furkansahin.todolist.service;

import com.furkansahin.todolist.exception.TodoNotFoundException;
import com.furkansahin.todolist.model.TodoItem;
import com.furkansahin.todolist.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService{

    private TodoItemRepository todoItemRepository;

    @Autowired
    public void setTodoItemRepository(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public List<TodoItem> findTodoItems() {
        return todoItemRepository.findAll();
    }

    @Override
    public TodoItem getTodoItemById(Long id) throws TodoNotFoundException {
        return todoItemRepository.findById(id).orElseThrow(()->{return new TodoNotFoundException("Todo Item Not found by id: " + id);});
    }

    @Override
    public TodoItem createTodo(TodoItem item) {
        todoItemRepository.save(item);
        return item;
    }

    @Override
    public void updateTodo(Long id, String str) {
        TodoItem item = todoItemRepository.getOne(id);
        item.setItem(str);
        todoItemRepository.save(item);
    }

    @Override
    public void deleteTodo(Long id) {
        todoItemRepository.deleteById(id);
    }
}
