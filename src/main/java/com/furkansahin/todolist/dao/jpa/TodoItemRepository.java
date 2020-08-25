package com.furkansahin.todolist.dao.jpa;

import com.furkansahin.todolist.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    @Query("UPDATE TodoItem i set i.item =?1 where i.id=?2")
    TodoItem updateTodoItem(String str, Long id);

    @Query("DELETE FROM TodoItem i where i.list =?1")
    void deleteTodoItemsByListId(Long id);


}