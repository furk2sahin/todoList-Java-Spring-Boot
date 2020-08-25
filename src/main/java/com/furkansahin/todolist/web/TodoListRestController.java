package com.furkansahin.todolist.web;

import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.exception.TodoNotFoundException;
import com.furkansahin.todolist.model.TodoItem;
import com.furkansahin.todolist.model.TodoList;
import com.furkansahin.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class TodoListRestController {

    @Autowired
    private TodoListService todoListService;

    @RequestMapping(method = RequestMethod.GET, value = "/todolists")
    public ResponseEntity<List<TodoList>> getLists(){
        List<TodoList> lists = todoListService.findLists();
        return ResponseEntity.ok(lists);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todolists/{title}")
    public ResponseEntity<?> getListsByNameLike(@PathVariable("title") String title){
        List<TodoList> lists = todoListService.findListsByNameLike(title);
        if(lists.isEmpty()){
            return new ResponseEntity<>("List not found by name: " + title, HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(lists);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/todolist/{id}")
    public ResponseEntity<?> getListById(@PathVariable("id") Long id){
        TodoList list = null;
        try{
           list = todoListService.getListById(id);
        } catch (ListNotFoundException ex){
            return new ResponseEntity<>(
                    "List Not found by id: " + id,
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todolist")
    public ResponseEntity<?> getListByTodoId(@RequestParam("todoId") Long todoId){
        TodoList list = null;

        try{
            list = todoListService.getListByTodoId(todoId);
        } catch (TodoNotFoundException ex){
            return new ResponseEntity<>("Todo item not found by id: " + todoId, HttpStatus.BAD_REQUEST);
        } catch (ListNotFoundException ex){
            return new ResponseEntity<>("List not found todo id: " + todoId, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todos/{id}")
    public ResponseEntity<?> getTodoItemById(@PathVariable("id") Long id){
        TodoItem item = null;
        try{
            item = todoListService.getTodoItemById(id);
        } catch (ListNotFoundException ex){
            return new ResponseEntity<>(
                    "Todo Item Not found by id: " + id,
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(item);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todos")
    public ResponseEntity<?> getTodoItems(){
        List<TodoItem> items = todoListService.findTodoItems();
        return ResponseEntity.ok(items);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/todolist")
    public ResponseEntity<TodoList> createList(@RequestBody TodoList list){
        try{
            todoListService.createList(list);
            Long id = list.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (ConstraintViolationException ex){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/todolist/{id}")
    public ResponseEntity<?> updateList(@PathVariable("id") Long id, @RequestBody TodoList todoListRequest){
        try {
            todoListService.updateList(todoListRequest);
            return ResponseEntity.ok().build();
        } catch (ListNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/todolist/{id}")
    public ResponseEntity<?> deleteList(@PathVariable("id") Long id){
        try{
            todoListService.getListById(id);
            todoListService.deleteList(id);
        } catch(ListNotFoundException ex){
            return new ResponseEntity<>("List not found by id: " + id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("List successfully deleted by id: " + id, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/todos")
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem item){
        try{
            todoListService.createTodo(item);
            Long id = item.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (ConstraintViolationException ex){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") Long id, @RequestBody TodoItem todoItemRequest){
        try {
            todoListService.updateTodo(todoItemRequest);
            return ResponseEntity.ok().build();
        } catch (ListNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id){
        try{
            todoListService.getTodoItemById(id);
            todoListService.deleteTodo(id);
        } catch(TodoNotFoundException ex){
            return new ResponseEntity<>("Todo Item not found by id: " + id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Todo Item successfully deleted by id: " + id, HttpStatus.OK);
    }
}
