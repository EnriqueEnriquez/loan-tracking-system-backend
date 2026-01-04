package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.PaymentAllocationStatus;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_allocation")
public class PaymentAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allocationId;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    })
    private GroupMember groupMember;

    @Column(nullable = false)
    private BigDecimal percent;

    @Column(nullable = false)
    private BigDecimal amount;

    private BigDecimal amountPaid = BigDecimal.ZERO;    // default

    @Enumerated(EnumType.STRING)
    private PaymentAllocationStatus paymentAllocationStatus = PaymentAllocationStatus.UNPAID;

    @ManyToOne
    @JoinColumn(name = "groupExpense", nullable = false)
    private GroupExpense groupExpense;

    public boolean isPaid() { return paymentAllocationStatus == PaymentAllocationStatus.PAID;}
    public boolean isEditable() { return paymentAllocationStatus == PaymentAllocationStatus.UNPAID;}
}