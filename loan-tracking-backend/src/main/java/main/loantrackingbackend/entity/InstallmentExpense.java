package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.PaymentFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "installment_expense")
public class InstallmentExpense extends Entry{

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Person personBorrower;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentFrequency paymentFrequency;

    @Column(nullable = false)
    private int paymentTerms;

    @Column(nullable = false)
    private BigDecimal paymentAmountPerTerm;

    @OneToMany(mappedBy = "installment_expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstallmentTerm> installmentTerms = new ArrayList<>();
}
