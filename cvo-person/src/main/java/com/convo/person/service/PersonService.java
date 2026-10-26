package com.convo.person.service;

import com.convo.person.controller.PersonController;
import com.convo.person.dto.PersonDTO;
import com.convo.person.entity.Person;
import com.convo.person.exception.PersonNotFoundException;
import com.convo.person.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// For REST call to cvo-file microservice
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO getPerson(Long personId) {
        PersonDTO personDTO = personRepository.findById(personId)
                .map(PersonDTO::from)
                .orElseThrow(() -> new PersonNotFoundException(personId));

        return personDTO;

    }

    public Resource getPersonBook(Long personId) throws IOException {
        PersonDTO personDTO = personRepository.findById(personId)
                .map(PersonDTO::from)
                .orElseThrow(() -> new PersonNotFoundException(personId));

        String uuid = personDTO.bookFileUuid();
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalStateException(
                    "Person " + personId + " has no book file UUID");
        }

        byte[] contents = fileClient.get()
                .uri("/api/v1/file/{uuid}", uuid)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .body(byte[].class);

        if (contents == null) {
            throw new IllegalStateException(
                    "File-service returned no content for UUID " + uuid);
        }

        return new ByteArrayResource(contents);
    }

    public List<PersonDTO> getAllPersons() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(PersonDTO::from)
                .collect(Collectors.toList());
    }

    public PersonDTO createPerson(PersonDTO personDTO, MultipartFile file) throws IOException {
        Person person = personDTO.toEntity();
        String uuid = uploadFile(personDTO, file);
        person.setBookFileUuid(uuid);
        personRepository.save(person);
        return PersonDTO.from(person);
    }

    private final RestClient fileClient = RestClient.builder()
            .baseUrl("http://localhost:8081")
            .build();

    public record FileUploadResponse(
            Long id, String name, String description, String uuid) {}

    private String uploadFile(PersonDTO personDTO, MultipartFile file) {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("name", "person's file: " + personDTO.name());
        parts.add("description", personDTO.description());
        parts.add("file", file.getResource());

        FileUploadResponse response = fileClient.post()
                .uri("/api/v1/file/create")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(parts)
                .retrieve()
                .body(FileUploadResponse.class);

        if (response == null || response.uuid() == null
                || response.uuid().isBlank()) {
            throw new IllegalStateException(
                    "File-service returned no file UUID");
        }

        return response.uuid();
    }
}


