package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        PersonDto savedPerson = personService.createPerson(personDto);

        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable("id") Long personId) {
        PersonDto personDto = personService.getPersonById(personId);

        return new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<PersonDto> persons = personService.getAllPersons();

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePerson(@PathVariable("id") Long personId, @RequestBody PersonDto updatedPerson) {
       try {
           PersonDto personDto = personService.updatePerson(personId, updatedPerson);
           return new ResponseEntity<>(personDto, HttpStatus.OK);
       } catch (Exception e) {
           Map<String, String> response = Collections.singletonMap("message", e.getMessage());

           return new ResponseEntity<>(response, HttpStatus.CONFLICT);
       }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deletePerson(@PathVariable("id") Long personId) {

        String resultMessage = personService.deletePerson(personId);

        Map<String, String> response = Collections.singletonMap("message", resultMessage);

        if (resultMessage.startsWith("Cannot")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
