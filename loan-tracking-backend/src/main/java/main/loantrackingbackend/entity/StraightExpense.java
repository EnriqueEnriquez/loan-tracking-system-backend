package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "straight_expense")
public class StraightExpense extends Entry {

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Person personBorrower;

    @Override
    public String getBorrowerName() {
        return personBorrower != null ?
                personBorrower.getFirstName() + " " + personBorrower.getLastName() :
                "Unknown";
    }

}
