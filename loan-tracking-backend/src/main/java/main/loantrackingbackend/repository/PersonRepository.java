package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
