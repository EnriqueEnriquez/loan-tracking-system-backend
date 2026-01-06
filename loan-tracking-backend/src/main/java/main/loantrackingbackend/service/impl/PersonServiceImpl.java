package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.PersonMapper;
import main.loantrackingbackend.repository.*;
import main.loantrackingbackend.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

        boolean isNameChanging = !person.getFirstName().equals(updatedPerson.getFirstName()) ||
                !person.getLastName().equals(updatedPerson.getLastName());

        if (isNameChanging) {
            List<PaymentStatus> activeStatuses = Arrays.asList(
                    PaymentStatus.UNPAID,
                    PaymentStatus.PARTIALLY_PAID
            );

            boolean hasExpenses =
                    entryRepository.existsByPersonLender(person)
                            || straightExpenseRepository.existsByPersonBorrower(person)
                            || installmentExpenseRepository.existsByPersonBorrower(person)
                            || paymentAllocationRepository.existsByGroupMemberBorrower(person);

            if (hasExpenses) {
                throw new IllegalStateException("Cannot update Name for person with id: " + personId + " because they have associated expenses");
            }
        }

        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        person.setContact(updatedPerson.getContact()); // Always allow contact updates

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
