package main.loantrackingbackend.entity.embedabble;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberId implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "person_id")
    private Long personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupMemberId)) return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(personId, that.personId) &&
                Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, personId);
    }
}
