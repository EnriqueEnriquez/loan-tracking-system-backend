package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.InstallmentStatus;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termId;

    @Column(nullable = false)
    private int termNumber;

    @Column(nullable = false)
    private LocalDate dueDate;

    private String notes;

    //Not stored in DB (not sure if this should be removed)
//  @Enumerated(EnumType.STRING)
//  private InstallmentStatus installmentStatus = InstallmentStatus.NOT_STARTED;

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private InstallmentExpense installmentExpense;
}
