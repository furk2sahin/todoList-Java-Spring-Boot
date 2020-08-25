package com.furkansahin.todolist.dao.jpa;

import com.furkansahin.todolist.model.TodoItem;
import com.furkansahin.todolist.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    @Query("Select lists from TodoList lists where lists.title like %?1%")
    List<TodoList> findTodoListByNameLike(String keyword);

    @Query("UPDATE TodoList l set l.title =?1 where l.id=?2")
    TodoItem updateTodoList(String title, Long id);
}