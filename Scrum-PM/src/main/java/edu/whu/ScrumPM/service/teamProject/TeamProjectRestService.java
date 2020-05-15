package edu.whu.ScrumPM.service.teamProject;


import edu.whu.ScrumPM.dao.teamProject.TeamProject;

import java.util.List;

public interface TeamProjectRestService {

     TeamProject saveTeamProject(TeamProject teamProject);

     void deleteTeamProject(Long teamProjectId);

     void updateTeamProject(TeamProject teamProject);

     TeamProject getTeamProject(Long teamProjectId);

     List<TeamProject> getAll();
}