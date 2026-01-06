package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    Entry findEntryById(UUID entryId);
    GroupExpense findGEById(UUID entryId);

    List<Entry> findAllByPaymentsPayeePersonId(Long payeeId);

    boolean existsByPersonLender(Person personLender);

}
