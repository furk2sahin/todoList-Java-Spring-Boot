package com.furkansahin.todolist.controller;

import com.furkansahin.todolist.exception.ListNotFoundException;
import com.furkansahin.todolist.model.TodoList;
import com.furkansahin.todolist.service.TodoListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Api(value = "TodoList Controller", description = "API for todo list operations")
@RestController
@RequestMapping("/rest")
public class TodoListRestController {

    @Autowired
    private TodoListService todoListService;

    @ApiOperation(value = "Get all todo lists", notes = "returns all lists")
    @RequestMapping(method = RequestMethod.GET, value = "/todolists")
    public ResponseEntity<List<TodoList>> getLists(){
        List<TodoList> lists = todoListService.findLists();
        return ResponseEntity.ok(lists);
    }

    @ApiOperation(value = "Get todo list by name containing", notes = "returns all lists which it contains given keyword")
    @RequestMapping(method = RequestMethod.GET, value = "/todolists/{title}")
    public ResponseEntity<?> getListsByNameLike(@PathVariable("title") String title){
        List<TodoList> lists = todoListService.findListsByNameLike(title);
        if(lists.isEmpty()){
            return new ResponseEntity<>("List not found by name: " + title, HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(lists);
        }

    }

    @ApiOperation(value = "Get list by id", notes = "returns a list which have the given id")
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

    @ApiOperation(value = "Create a list", notes = "Creates a list and returns it")
    @RequestMapping(method = RequestMethod.POST, value = "/todolist")
    public ResponseEntity<TodoList> createList(@RequestBody TodoList list){
        try{
            todoListService.createList(list);
            return ResponseEntity.ok(list);
        } catch (ConstraintViolationException ex){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Update a list", notes = "Updates a list and rename it given title")
    @RequestMapping(method = RequestMethod.PUT, value = "/todolist/{id}")
    public ResponseEntity<?> updateList(@PathVariable("id") Long id, @RequestBody TodoList todoListRequest){
        try {
            todoListService.updateList(id, todoListRequest.getTitle());
            return new ResponseEntity<>("Todo List title updated!", HttpStatus.OK);
        } catch (ListNotFoundException e) {
            return new ResponseEntity<>(
                    "List Not found by id: " + id,
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Delete a list", notes = "Deletes a list by given id")
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
}
