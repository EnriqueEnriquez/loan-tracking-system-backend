package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entry")
@Inheritance(strategy = InheritanceType.JOINED)
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String entryName;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    private LocalDate dateBorrowed;
    private LocalDate dateFullyPaid;

    // to be decided if needed for this table
    @Column(nullable = false)
    private String borrowerName;

    @Column(nullable = false)
    private String lenderName;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Person personLender;

    @Column(nullable = false)
    private BigDecimal amountBorrowed;

    @Column(nullable = false)
    private BigDecimal amountRemaining;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String notes;

    //@Column(nullable = false)
    //TODO: Create Logic for Reference ID
    //private String referenceId;

    // borrower + lender or group name + lender

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageProof> imageProofFiles = new ArrayList<>();

}
