package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "image_proofs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "entry_id")
    private Entry entry;
}
