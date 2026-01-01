package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.PaymentFrequency;
import main.loantrackingbackend.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

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


}
