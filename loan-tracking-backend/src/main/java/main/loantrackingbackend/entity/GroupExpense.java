package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_expense")
public class GroupExpense extends Entry {
    @ManyToOne
    @JoinColumn(name = "borrower_group_id")
    private Group groupBorrower;

    @OneToMany(mappedBy = "groupExpense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentAllocation> paymentAllocations = new ArrayList<>();

    public void addPaymentAllocation(PaymentAllocation allocation) {
        paymentAllocations.add(allocation);
        allocation.setGroupExpense(this);
    }

    public void removePaymentAllocation(PaymentAllocation allocation) {
        paymentAllocations.remove(allocation);
        allocation.setGroupExpense(null);
    }
}