package edu.whu.ScrumPM.dao.teamRelationship;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="teamRelationship")
public class TeamRelationship {
    @Id
    @GeneratedValue
    private Long id;
    private Long teamProjectId;
    private Long userId;
    private String userState;
}
