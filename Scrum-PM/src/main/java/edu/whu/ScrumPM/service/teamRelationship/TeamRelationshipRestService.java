package edu.whu.ScrumPM.service.teamRelationship;


import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.dao.teamRelationship.TeamRelationship;

import java.util.List;

public interface TeamRelationshipRestService {

     TeamRelationship saveTeamRelationship(TeamRelationship teamRelationship);

     void deleteTeamRelation(Long id);

     void updateTeamRelationship(TeamRelationship teamRelationship);

     List<TeamRelationship> getTeamRelationshipByUserId(Long userId);
     List<TeamRelationship> getTeamRelationshipByTeamProjectId(Long teamProjectId);
}