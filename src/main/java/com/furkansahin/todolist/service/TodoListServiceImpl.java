package com.furkansahin.todolist.service;

import com.furkansahin.todolist.dao.jpa.TodoItemRepository;
import com.furkansahin.todolist.dao.jpa.TodoListRepository;
import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.exception.TodoNotFoundException;
import com.furkansahin.todolist.model.TodoItem;
import com.furkansahin.todolist.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return todoListRepository.findTodoListByNameLike(keyword);
    }

    @Override
    public TodoList getListById(Long id) throws ListNotFoundException {
        return todoListRepository.findById(id).orElseThrow(()->{return new ListNotFoundException("List Not found by id: " + id);});
    }

    @Override
    public TodoList getListByTodoId(Long id) throws ListNotFoundException {
        Optional<TodoItem> item = todoItemRepository.findById(id);
        if(item.isEmpty()){
            throw new TodoNotFoundException("Todo not found by id: " + id);
        } else{
           return todoListRepository.findById(item.get().getList().getId()).orElseThrow(()->{return new ListNotFoundException("List Not found by id: " + id);});
        }
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
    public void createList(TodoList list) {
        todoListRepository.save(list);
    }

    @Override
    public void updateList(TodoList list) {
        todoListRepository.updateTodoList(list.getTitle(), list.getId());
    }

    @Override
    public void deleteList(Long id) {
        todoItemRepository.deleteTodoItemsByListId(id);
        todoListRepository.deleteById(id);
    }

    @Override
    public void createTodo(TodoItem item) {
        todoItemRepository.save(item);
    }

    @Override
    public void updateTodo(TodoItem item) {
        todoItemRepository.updateTodoItem(item.getItem(), item.getId());
    }

    @Override
    public void deleteTodo(Long id) {
        todoItemRepository.deleteById(id);
    }
}
