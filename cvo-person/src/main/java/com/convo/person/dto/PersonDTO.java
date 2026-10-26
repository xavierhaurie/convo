package com.convo.person.dto;

import com.convo.person.entity.Person;

public record PersonDTO(Long id, boolean isNpc, String name, String description, String bookFileUuid) {

    static public PersonDTO from(Person personEntity) {
        // always NPC for now.
        return new PersonDTO(personEntity.getId(), personEntity.getIsNpc(), personEntity.getName(), personEntity.getDescription(), personEntity.getBookFileUuid());
    }

    // bookFileUuid field excluded on purpose
    public Person toEntity() {
        Person personEntity = new Person();
        personEntity.setIsNpc(this.isNpc);
        personEntity.setName(this.name);
        personEntity.setDescription(this.description);
        personEntity.setBookFileUuid(this.bookFileUuid);
        return personEntity;
    }
}
