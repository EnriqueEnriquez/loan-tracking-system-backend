package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "payment_proof")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proofId;

    private String imageName;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
