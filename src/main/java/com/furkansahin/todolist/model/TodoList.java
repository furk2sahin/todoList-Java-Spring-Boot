package com.furkansahin.todolist.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "Todos")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoListSeqGen")
    @SequenceGenerator(name = "todoListSeqGen", sequenceName = "todolist_sequence")
    private Long id;

    @NotEmpty
    @Column(name = "list_title")
    private String title;

    @CreatedDate
    private Date createDate;

    @OneToMany(mappedBy = "list")
    private List<TodoItem> items = new ArrayList<TodoItem>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
