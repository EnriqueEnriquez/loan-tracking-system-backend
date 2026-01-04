package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.GroupExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    Entry findEntryById(UUID entryId);
    GroupExpense findGEById(UUID entryId);

    List<Entry> findAllByPaymentsPayeePersonId(Long payeeId);
}
