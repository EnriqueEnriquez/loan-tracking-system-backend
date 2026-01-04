package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPayee(Person payee);
    List<Payment> findByEntry(Entry entry);
    void deleteByPayeePersonId(Long personId);
    void deleteByEntryId(UUID entryId);
}
