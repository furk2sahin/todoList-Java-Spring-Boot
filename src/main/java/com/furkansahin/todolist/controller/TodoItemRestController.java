package com.furkansahin.todolist.controller;

import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.exception.TodoNotFoundException;
import com.furkansahin.todolist.model.TodoItem;
import com.furkansahin.todolist.model.TodoList;
import com.furkansahin.todolist.service.TodoItemService;
import com.furkansahin.todolist.service.TodoListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Api(value = "Todo item Controller", description = "API for todo item operations")
@RestController
@RequestMapping("/rest")
public class TodoItemRestController {

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private TodoListService todoListService;

    @ApiOperation(value = "Get all todo items", notes = "returns all todo items")
    @RequestMapping(method = RequestMethod.GET, value = "/todos")
    public ResponseEntity<?> getTodoItems(){
        List<TodoItem> items = todoItemService.findTodoItems();
        return ResponseEntity.ok(items);
    }

    @ApiOperation(value = "Get todo item by id", notes = "returns a item which have the given id")
    @RequestMapping(method = RequestMethod.GET, value = "/todo/{id}")
    public ResponseEntity<?> getTodoItemById(@PathVariable("id") Long id){
        TodoItem item = null;
        try{
            item = todoItemService.getTodoItemById(id);
        } catch (TodoNotFoundException ex){
            return new ResponseEntity<>(
                    "Todo Item Not found by id: " + id,
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(item);
    }

    @ApiOperation(value = "Create a todo item", notes = "Creates a todo item and returns it")
    @RequestMapping(method = RequestMethod.POST, value = "/todo")
    public ResponseEntity<TodoItem> createTodo(@RequestParam("listId") Long listId, @RequestBody TodoItem item){
        TodoItem createdItem = null;
        TodoList list = todoListService.getListById(listId);
        item.setList(list);
        try{
            createdItem = todoItemService.createTodo(item);
            return ResponseEntity.ok(createdItem);
        } catch (ConstraintViolationException ex){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Update a todo item", notes = "Updates a todo item and rename it given item")
    @RequestMapping(method = RequestMethod.PUT, value = "/todo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") Long id, @RequestBody TodoItem todoItemRequest){
        try {
            todoItemService.updateTodo(id, todoItemRequest.getItem());
            return new ResponseEntity<>("Todo List title updated!", HttpStatus.OK);
        } catch (ListNotFoundException e) {
            return new ResponseEntity<>(
                    "List Not found by id: " + id,
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Delete a todo item", notes = "Deletes a todo item by given id")
    @RequestMapping(method = RequestMethod.DELETE, value = "/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id){
        try{
            todoItemService.getTodoItemById(id);
            todoItemService.deleteTodo(id);
        } catch(TodoNotFoundException ex){
            return new ResponseEntity<>("Todo Item not found by id: " + id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Todo Item successfully deleted by id: " + id, HttpStatus.OK);
    }
}
