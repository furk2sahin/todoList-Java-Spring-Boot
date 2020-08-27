package com.furkansahin.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

@Entity
@Table(name = "TodoList")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoListSeqGen")
    @SequenceGenerator(name = "todoListSeqGen", sequenceName = "todolist_sequence")
    private Long id;

    @NotEmpty
    @Column(name = "list_title")
    private String title;

    @CreationTimestamp
    private Date createDate;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = TodoItem.class)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    private List<TodoItem> items = new ArrayList<>();

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

    public Date getCreateDate() {
        return createDate;
    }

    public List<TodoItem> getItems() {
        return items;
    }

    public void setItems(List<TodoItem> items) {
        this.items = items;
    }
}
