package main.loantrackingbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.entity.embedabble.GroupMemberId;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_members")
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}


