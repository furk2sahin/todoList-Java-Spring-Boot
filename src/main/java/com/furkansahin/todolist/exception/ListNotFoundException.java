package com.furkansahin.todolist.exception;

public class ListNotFoundException extends RuntimeException {
    public ListNotFoundException(String message){
        super(message);
    }
}
