package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.exception.DuplicateResourceException;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.PersonMapper;
import main.loantrackingbackend.repository.*;
import main.loantrackingbackend.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PaymentAllocationRepository paymentAllocationRepository;
    private StraightExpenseRepository straightExpenseRepository;
    private InstallmentExpenseRepository installmentExpenseRepository;
    private EntryRepository entryRepository;
    private PersonRepository personRepository;

    @Override
    public PersonDto createPerson(PersonDto personDto) {

        if (personRepository.existsByFirstNameAndLastName(personDto.getFirstName(), personDto.getLastName())) {
            throw new DuplicateResourceException("Person with name " + personDto.getFirstName() + " " + personDto.getLastName() + " already exists");
        }

        if (personRepository.existsByContact(personDto.getContact())) {
            throw new DuplicateResourceException("Contact " + personDto.getContact() + " already exists");
        }


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

        if (personRepository.existsByFirstNameAndLastName(updatedPerson.getFirstName(), updatedPerson.getLastName())) {
            throw new DuplicateResourceException("Person with name " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName() + " already exists");
        }

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + personId));

        if (personRepository.existsByContact(updatedPerson.getContact()) && !Objects.equals(person.getContact(), updatedPerson.getContact())) {
            throw new DuplicateResourceException("Contact " + updatedPerson.getContact() + " already exists");
        }

        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());

        if (!Objects.equals(person.getContact(), updatedPerson.getContact())) {
            person.setContact(updatedPerson.getContact());
        }

        Person savedPerson = personRepository.save(person);

        return PersonMapper.mapToPersonDto(savedPerson);
    }

    @Override
    public String deletePerson(Long personId) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person does not exist with id: " + personId));

        List<PaymentStatus> activeStatuses = Arrays.asList(
                PaymentStatus.UNPAID,
                PaymentStatus.PARTIALLY_PAID
        );

        if (entryRepository.existsByPersonLender(person)
            || straightExpenseRepository.existsByPersonBorrower(person)
            || installmentExpenseRepository.existsByPersonBorrower(person)
            || paymentAllocationRepository.existsByGroupMemberBorrower(person)) {
            return "Cannot delete person with id: " + personId + " because they are associated with expense/s. Delete those expense first.";
        }

        personRepository.delete(person);

        return "Person with id: " + personId + " was successfully deleted";
    }
}
