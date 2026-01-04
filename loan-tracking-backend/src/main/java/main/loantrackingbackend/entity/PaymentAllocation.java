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

    @ManyToOne
    @JoinColumn(name = "groupExpense", nullable = false)
    private GroupExpense groupExpense;

    public BigDecimal getAmountPaid() {
        if (groupExpense == null || groupExpense.getPayments() == null) {
            return BigDecimal.ZERO;
        }

        return groupExpense.getPayments()
                .stream()
                .filter(payment -> payment.getPayee().getPersonId().equals(groupMember.getPerson().getPersonId()))
                .map(Payment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PaymentAllocationStatus getPaymentAllocationStatus() {
        BigDecimal paid = getAmountPaid();
        if (paid.compareTo(BigDecimal.ZERO) == 0) return PaymentAllocationStatus.UNPAID;
        if (paid.compareTo(amount) < 0) return PaymentAllocationStatus.PARTIALLY_PAID;
        return PaymentAllocationStatus.PAID;
    }

    public boolean isPaid() {
        return getPaymentAllocationStatus() == PaymentAllocationStatus.PAID;
    }

    public boolean isEditable() {
        return getPaymentAllocationStatus() == PaymentAllocationStatus.UNPAID;
    }
}