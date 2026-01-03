package main.loantrackingbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "installment_term")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID installmentID;

    private int termNumber;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private InstallmentExpense installmentExpense;
}
