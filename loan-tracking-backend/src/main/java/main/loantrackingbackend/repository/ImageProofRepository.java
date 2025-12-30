package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.ImageProof;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageProofRepository extends JpaRepository<ImageProof, Long> {
}
