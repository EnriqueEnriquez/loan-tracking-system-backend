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

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Person personLender;

    @Column(nullable = false)
    private BigDecimal amountBorrowed;

    @Column(nullable = false)
    private BigDecimal amountRemaining;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.UNPAID;

    private String notes;
    private String referenceId;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageProof> imageProofFiles = new ArrayList<>();

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    // Add a helper method to get the name dynamically
    public String getLenderName() {
        return personLender != null ?
                personLender.getFirstName() + " " + personLender.getLastName() :
                "Unknown";
    }

    public String getBorrowerName() {
        return "";
    }
}