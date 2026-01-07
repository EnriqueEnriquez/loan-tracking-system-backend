package main.loantrackingbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "image_proofs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private Entry entry;
}
