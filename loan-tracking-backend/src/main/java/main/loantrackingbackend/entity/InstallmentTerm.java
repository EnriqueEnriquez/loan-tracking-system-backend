package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.InstallmentStatus;
import main.loantrackingbackend.util.TestDateManager;

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
}