package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.entity.Person;

public class PersonMapper {

    public static PersonDto mapToPersonDto(Person person) {
        return new PersonDto(
                person.getPersonId(),
                person.getFirstName(),
                person.getLastName(),
                person.getContact()
        );
    }

    public static Person mapToPerson(PersonDto personDto) {
        Person person = new Person();
        person.setPersonId(personDto.getPersonId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setContact(personDto.getContact());

        return person;
    }
}
