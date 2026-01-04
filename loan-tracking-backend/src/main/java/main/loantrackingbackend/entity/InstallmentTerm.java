package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.InstallmentStatus;
import main.loantrackingbackend.enums.PaymentFrequency;
import main.loantrackingbackend.util.TestDateManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

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

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private InstallmentExpense installmentExpense;

    public InstallmentStatus getInstallmentStatus(boolean skipped, boolean paid) {
        if(TestDateManager.today().isBefore(installmentExpense.getStartDate())) return InstallmentStatus.NOT_STARTED;
        if(skipped) return InstallmentStatus.SKIPPED;
        if(paid) return InstallmentStatus.PAID;
        if(TestDateManager.today().isAfter(dueDate)) return InstallmentStatus.DELINQUENT;
        return InstallmentStatus.UNPAID;
    }

    /* useful but unsure where to use methods
    public BigDecimal getExpectedAmountPaid() {
        return installmentExpense.getPaymentAmountPerTerm().multiply(BigDecimal.valueOf(termNumber));
    }

    public BigDecimal getAmountPaid() {
        if (installmentExpense == null || installmentExpense.getPayments() == null) {
            return BigDecimal.ZERO;
        }

        return installmentExpense.getPayments()
                .stream()
                .filter(payment -> payment.getPayee().getPersonId().equals(installmentExpense.getPersonBorrower().getPersonId()))
                .map(Payment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    } */

    public void computeDueDate() {
        LocalDate startDate = installmentExpense.getStartDate();
        PaymentFrequency frequency = installmentExpense.getPaymentFrequency();
        DayOfWeek weeklyPayDay = installmentExpense.getWeeklyPayDay();

        if (frequency == PaymentFrequency.MONTHLY) {
            dueDate = startDate.plusMonths(termNumber - 1);

        } else if (frequency == PaymentFrequency.WEEKLY) {
            LocalDate tempDate = startDate;
            tempDate = tempDate.with(TemporalAdjusters.nextOrSame(weeklyPayDay));
            dueDate = tempDate.plusWeeks(termNumber - 1);
        }
    }

    public LocalDate getDueDate() {
        if (dueDate == null) computeDueDate();
        return dueDate;
    }
}