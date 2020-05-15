package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.teamRelationship.TeamRelationship;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.service.iteration.IterationRestService;
import edu.whu.ScrumPM.service.teamProject.TeamProjectRestService;
import edu.whu.ScrumPM.service.teamRelationship.TeamRelationshipRestService;
import edu.whu.ScrumPM.service.user.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class TeamRelationshipRestController {
    @Resource(name= "teamRelationshipRestJPAServiceImpl")
    TeamRelationshipRestService teamRelationshipRestService;

    @Resource(name= "teamProjectRestJPAServiceImpl")
    TeamProjectRestService teamProjectRestService;

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @CrossOrigin
    @PostMapping("/teamRelationship")
    public @ResponseBody
    AjaxResponse saveTeamRelationship(@RequestBody TeamRelationship teamRelationship) {
        try {
            teamProjectRestService.getTeamProject(teamRelationship.getTeamProjectId());
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的团队项目");
        }
        try {
            userRestService.getUserByUserId(teamRelationship.getUserId());
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的用户");
        }
        TeamRelationship successTeamRelationship= teamRelationshipRestService.saveTeamRelationship(teamRelationship);
        return AjaxResponse.success(successTeamRelationship);
    }

    @CrossOrigin
    @DeleteMapping("/teamRelationship/{id}")
    public @ResponseBody AjaxResponse deleteTeamRelationship(@PathVariable Long id) {
        try {
            teamRelationshipRestService.deleteTeamRelation(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的用户");
        }
    }

    @CrossOrigin
    @PutMapping("/teamRelationship")
    public @ResponseBody AjaxResponse updateTeamRelationship(@RequestBody TeamRelationship teamRelationship) {

        try{
            teamRelationshipRestService.saveTeamRelationship(teamRelationship);
            return AjaxResponse.success(teamRelationship);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

//    @GetMapping("/teamRelationship/{id}")
//    public @ResponseBody AjaxResponse getTeamRelationship(@PathVariable Long id) {
//        try{
//            List<TeamRelationship> teamRelationships=teamRelationshipRestService.getTeamRelationship(id);
//            return AjaxResponse.success(teamRelationships);
//        }catch (Exception e){
//            return AjaxResponse.fail(e);
//        }
//    }
}
