package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
