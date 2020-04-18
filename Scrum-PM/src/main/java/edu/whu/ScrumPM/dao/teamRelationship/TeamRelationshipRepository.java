package edu.whu.ScrumPM.dao.teamRelationship;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship,Long> {
    List<TeamRelationship> findTeamRelationshipByTeamProjectId(Long teamProjectId);
    List<TeamRelationship> findTeamRelationshipByUserId(Long userId);
}
