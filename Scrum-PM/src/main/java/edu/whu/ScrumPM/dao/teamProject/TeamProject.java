package edu.whu.ScrumPM.dao.teamProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="teamProject")
public class TeamProject {
    @Id
    @GeneratedValue
    private Long teamProjectId;
    private String teamProjectName;
}
