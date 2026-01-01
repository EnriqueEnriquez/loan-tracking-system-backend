package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.PaymentProof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentProofRepository extends JpaRepository<PaymentProof, Long> {
}
