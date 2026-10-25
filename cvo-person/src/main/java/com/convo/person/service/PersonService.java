package com.convo.person.service;

import com.convo.person.controller.PersonController;
import com.convo.person.dto.PersonDTO;
import com.convo.person.entity.Person;
import com.convo.person.exception.PersonNotFoundException;
import com.convo.person.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    // fill out later when we move methods from controller to service

    public PersonDTO getPerson(Long personId) {
        PersonDTO personDTO = personRepository.findById(personId)
                .map(PersonDTO::from)
                .orElseThrow(() -> new PersonNotFoundException(personId));

        return personDTO;

    }

    public Resource getPersonBook(Long personId) throws IOException {
        logger.info("PersonService.getPersonBook() called with Person id {}", personId);
        PersonDTO personDTO = personRepository.findById(personId)
                .map(PersonDTO::from)
                .orElseThrow(() -> new PersonNotFoundException(personId));
        logger.info("personRepository.findById() returning {}", personDTO);
        String url = personDTO.bookFileUuid();
        // get book from file cvo-file microservice
        File file = null;
        Resource resource = new org.springframework.core.io.FileSystemResource(file);
        return resource;
    }

    public List<PersonDTO> getAllPersons() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(PersonDTO::from)
                .collect(Collectors.toList());
    }

    public PersonDTO createPerson(PersonDTO personDTO, MultipartFile file) throws IOException {
        Person person = personDTO.toEntity();
        // Forward file to cvo-file service for storage and get back a file identifier (e.g., URL or ID) to store in the Person entity
        String url = null;
        person.setBookFileUuid(url);
        PersonDTO saved = PersonDTO.from(personRepository.save(person));

        return saved;
    }
}


