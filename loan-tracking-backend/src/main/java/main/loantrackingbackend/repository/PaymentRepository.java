package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPayee(Person payee);
}
