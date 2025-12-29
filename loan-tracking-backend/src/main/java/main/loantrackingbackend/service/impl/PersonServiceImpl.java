package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.PersonMapper;
import main.loantrackingbackend.repository.PersonRepository;
import main.loantrackingbackend.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Override
    public PersonDto createPerson(PersonDto personDto) {

        Person person = PersonMapper.mapToPerson(personDto);
        Person savedPerson = personRepository.save(person);

        return PersonMapper.mapToPersonDto(savedPerson);
    }

    @Override
    public PersonDto getPersonById(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + personId));

        return PersonMapper.mapToPersonDto(person);
    }

    @Override
    public List<PersonDto> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream().map((person) -> PersonMapper.mapToPersonDto(person)).collect(Collectors.toList());
    }

    @Override
    public PersonDto updatePerson(Long personId, PersonDto updatedPerson) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + personId));

        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        person.setContact(updatedPerson.getContact());

        Person savedPerson = personRepository.save(person);

        return PersonMapper.mapToPersonDto(savedPerson);
    }

    @Override
    public void deletePerson(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + personId));

        personRepository.delete(person);
    }
}
