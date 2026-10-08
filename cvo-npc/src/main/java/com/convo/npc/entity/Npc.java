package com.convo.npc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Npc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "book_url")
    String bookUrl;

    public Npc() {
    }

    public Npc(String name, String description, String bookUrl) {
        this.name = name;
        this.description = description;
        this.bookUrl = bookUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }


}
