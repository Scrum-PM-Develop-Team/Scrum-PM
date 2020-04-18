package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.teamRelationship.TeamRelationship;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.service.teamRelationship.TeamRelationshipRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class TeamRelationshipRestController {
    @Resource(name= "teamRelationshipRestJPAServiceImpl")
    TeamRelationshipRestService teamRelationshipRestService;

    @PostMapping("/teamRelationship")
    public @ResponseBody
    AjaxResponse saveTeamRelationship(@RequestBody TeamRelationship teamRelationship) {

        try {
            TeamRelationship successTeamRelationship= teamRelationshipRestService.saveTeamRelationship(teamRelationship);
            return AjaxResponse.success(successTeamRelationship);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @DeleteMapping("/teamRelationship/{id}")
    public @ResponseBody AjaxResponse deleteTeamRelationship(@PathVariable Long id) {
        try {
            teamRelationshipRestService.deleteTeamRelation(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

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
