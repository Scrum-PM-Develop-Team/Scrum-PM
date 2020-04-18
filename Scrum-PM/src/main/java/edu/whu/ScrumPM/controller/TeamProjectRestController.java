package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.service.teamProject.TeamProjectRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rest")
public class TeamProjectRestController {
    @Resource(name= "teamProjectRestJPAServiceImpl")
    TeamProjectRestService teamProjectRestService;

    @PostMapping("/teamProject")
    public @ResponseBody
    AjaxResponse saveTeamProject(@RequestBody TeamProject teamProject) {

        try {
            TeamProject successTeamProject= teamProjectRestService.saveTeamProject(teamProject);
            return AjaxResponse.success(successTeamProject);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @DeleteMapping("/teamProject/{id}")
    public @ResponseBody AjaxResponse deleteTeamProject(@PathVariable Long id) {
        try {
            teamProjectRestService.deleteTeamProject(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @PutMapping("/teamProject")
    public @ResponseBody AjaxResponse updateTeamProject(@RequestBody TeamProject teamProject) {

        try{
            teamProjectRestService.saveTeamProject(teamProject);
            return AjaxResponse.success(teamProject);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }
}
