package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    Entry findEntryById(UUID entryId);
    GroupExpense findGEById(UUID entryId);

    List<Entry> findAllByPaymentsPayeePersonId(Long payeeId);
    List<Entry> findAllByStatus(PaymentStatus paymentStatus);

    boolean existsByPersonLender(Person personLender);

    List<Entry> findAllByTransactionType(TransactionType transactionType);

}
