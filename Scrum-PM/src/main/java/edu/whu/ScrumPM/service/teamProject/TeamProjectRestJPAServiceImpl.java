package edu.whu.ScrumPM.service.teamProject;



import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.dao.teamProject.TeamProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeamProjectRestJPAServiceImpl implements TeamProjectRestService {

    @Resource
    private TeamProjectRepository teamProjectRepository;

    @Override
    public TeamProject saveTeamProject(TeamProject teamProject) {
        teamProjectRepository.save(teamProject);
        return teamProject;
    }

    @Override
    public void deleteTeamProject(Long teamProjectId) {
        teamProjectRepository.deleteById(teamProjectId);
    }

    @Override
    public void updateTeamProject(TeamProject teamProject) {
        teamProjectRepository.save(teamProject);
    }

    @Override
    public TeamProject getTeamProject(Long teamProjectId) {
        Optional<TeamProject> teamProject=teamProjectRepository.findByTeamProjectId(teamProjectId);
        return teamProject.get();
    }

    @Override
    public List<TeamProject> getAll() {
        return teamProjectRepository.findAll();
    }
}
