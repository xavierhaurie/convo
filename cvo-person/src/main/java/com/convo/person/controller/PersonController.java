package com.convo.person.controller;

import com.convo.person.service.PersonService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import com.convo.person.dto.PersonDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/person/{personId}")
    public PersonDTO getPerson(@PathVariable Long personId) {
        logger.info("getPerson() called with person_id {}", personId);
        return personService.getPerson(personId);
    }

    @GetMapping("/person/{personId}/book")
    public ResponseEntity<Resource> getPersonBook(@PathVariable Long personId) {
        logger.info("getPersonBook() called with Person id {}", personId);
        try {
            Resource resource = personService.getPersonBook(personId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (Exception e) {
            String errorMessage = "Error downloading book for PERSON id " + personId;
            logger.error(errorMessage, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/persons")
    public List<PersonDTO> getAllPersons(
    ) {
        logger.info("getAllPersons() called");

        List<PersonDTO> persons = personService.getAllPersons();

        logger.info("getAllPersons() is returning {} records", persons.size());

        return persons;
    }

    @PostMapping(value = "/person/create", consumes = "multipart/form-data")
    public ResponseEntity<PersonDTO> createPerson(
            @ModelAttribute PersonDTO personDTO,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("Received empty file to upload");
            return ResponseEntity.status(500).build();
        }
        logger.info("Received upload: {} ({} bytes)",
                file.getOriginalFilename(), file.getSize());
        try {
            // REST call to Cvo file service to save the file and get back the file UUID
            // Handle HTTP errors
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            String errorMessage = "Could not reach CVO file service to save the file just uploaded: " + file.getOriginalFilename();
            logger.error(errorMessage, e);
            return ResponseEntity.status(500).build();
        }
    }

}