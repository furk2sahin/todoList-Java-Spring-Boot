package com.furkansahin.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "TodoItem")
@JsonIgnoreProperties("list")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoListSeqGen")
    @SequenceGenerator(name = "todoListSeqGen", sequenceName = "todolist_sequence")
    private Long id;

    @Column(name = "item")
    private String item;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = TodoList.class)
    private TodoList list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public TodoList getList() {
        return list;
    }

    public void setList(TodoList list) {
        this.list = list;
    }
}
