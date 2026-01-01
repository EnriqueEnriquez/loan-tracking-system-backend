package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {

}
