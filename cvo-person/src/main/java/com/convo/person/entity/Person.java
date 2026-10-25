package com.convo.person.entity;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "book_file_uuid")
    String bookFileUuid;
    @Column(name = "is_npc")
    Boolean isNpc;

    public Person() {
    }

    public Person(String name, String description, String bookFileUuid, Boolean isNpc) {
        this.name = name;
        this.description = description;
        this.bookFileUuid = bookFileUuid;
        this.isNpc = isNpc;
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

    public String getBookFileUuid() {
        return bookFileUuid;
    }

    public void setBookFileUuid(String bookFileUuid) {
        this.bookFileUuid = bookFileUuid;
    }

    public Boolean getIsNpc() {
        return isNpc;
    }

    public void setIsNpc(Boolean isNpc) {
        this.isNpc = isNpc;
    }
}
