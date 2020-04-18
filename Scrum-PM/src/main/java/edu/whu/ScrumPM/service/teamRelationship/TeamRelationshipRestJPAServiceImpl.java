package edu.whu.ScrumPM.service.teamRelationship;



import edu.whu.ScrumPM.dao.teamProject.TeamProjectRepository;
import edu.whu.ScrumPM.dao.teamRelationship.TeamRelationship;
import edu.whu.ScrumPM.dao.teamRelationship.TeamRelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TeamRelationshipRestJPAServiceImpl implements TeamRelationshipRestService {

    @Resource
    private TeamRelationshipRepository teamRelationshipRepository;

    @Override
    public TeamRelationship saveTeamRelationship(TeamRelationship teamRelationship) {
        teamRelationshipRepository.save(teamRelationship);
        return teamRelationship;
    }

    @Override
    public void deleteTeamRelation(Long id) {
        teamRelationshipRepository.deleteById(id);
    }

    @Override
    public void updateTeamRelationship(TeamRelationship teamRelationship) {
        teamRelationshipRepository.save(teamRelationship);
    }

    @Override
    public List<TeamRelationship> getTeamRelationshipByUserId(Long userId) {
        return teamRelationshipRepository.findTeamRelationshipByUserId(userId);
    }

    @Override
    public List<TeamRelationship> getTeamRelationshipByTeamProjectId(Long teamProjectId) {
        return teamRelationshipRepository.findTeamRelationshipByTeamProjectId(teamProjectId);
    }
}
