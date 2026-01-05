package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.InstallmentStatus;
import main.loantrackingbackend.util.TestDateManager;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "installment_term")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termId;

    @Column(nullable = false)
    private int termNumber;

    @Column(nullable = false)
    private LocalDate dueDate;

    private String notes;
    private boolean skipped;

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private InstallmentExpense installmentExpense;

    @OneToOne @JoinColumn(name = "term_id")
    private Payment payment;

    public InstallmentStatus getInstallmentStatus() {
        BigDecimal termAmount = installmentExpense.getPaymentAmountPerTerm();
        BigDecimal paid = getAmountPaid();

        if(TestDateManager.today().isBefore(installmentExpense.getStartDate())) return InstallmentStatus.NOT_STARTED;
        if(skipped) return InstallmentStatus.SKIPPED;
        if(paid.compareTo(termAmount) >= 0) return InstallmentStatus.PAID;
        if(TestDateManager.today().isAfter(dueDate)) return InstallmentStatus.DELINQUENT;
        return InstallmentStatus.UNPAID;
    }

    public BigDecimal getAmountPaid() {
        if (installmentExpense == null || installmentExpense.getPayments() == null)
            return BigDecimal.ZERO;

        // Sum only the payments applied to this expense
        return installmentExpense.getPayments().stream()
                .filter(payment -> payment.getPayee().getPersonId()
                        .equals(installmentExpense.getPersonBorrower().getPersonId()))
                .map(Payment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}