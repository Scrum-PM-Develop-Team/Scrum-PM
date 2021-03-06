package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.iteration.Iteration;
import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.model.IterationVO;
import edu.whu.ScrumPM.model.TeamProjectVO;
import edu.whu.ScrumPM.service.iteration.IterationRestService;
import edu.whu.ScrumPM.service.teamProject.TeamProjectRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rest")
public class IterationRestController {
    @Resource(name= "iterationJPARestServiceImpl")
    IterationRestService iterationRestService;

    @Resource(name= "teamProjectRestJPAServiceImpl")
    TeamProjectRestService teamProjectRestService;

    @CrossOrigin
    @PostMapping("/iteration") public @ResponseBody
    AjaxResponse saveIteration(@RequestBody TeamProjectVO teamProjectVO) {
        IterationVO iterationVO=teamProjectVO.getIterationVOs().get(0);
        try {
            teamProjectRestService.getTeamProject(teamProjectVO.getTeamProjectId());
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的团队项目");
        }
        Iteration iteration=Iteration.builder()
                .teamProjectId(teamProjectVO.getTeamProjectId())
                .iterationBeginTime(iterationVO.getIterationBeginTime())
                .iterationEndTime(iterationVO.getIterationEndTime())
                .iterationName(iterationVO.getIterationName())
                .iterationState(iterationVO.getIterationState())
                .build();
        iterationVO.setIterationId(iterationRestService.saveIteration(iteration).getIterationId());
        return AjaxResponse.success(iterationVO);
    }

    @CrossOrigin
    @DeleteMapping("/iteration/{id}")
    public @ResponseBody AjaxResponse deleteIteration(@PathVariable Long id){
        try {
            iterationRestService.deleteIteration(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的迭代");
        }
    }

    @CrossOrigin
    @PutMapping("/iteration")
    public @ResponseBody AjaxResponse updateIteration(@RequestBody IterationVO iterationVO) {

        try{
            Iteration iteration= iterationRestService.getIteration(iterationVO.getIterationId());
            iteration.setIterationBeginTime(iterationVO.getIterationBeginTime());
            iteration.setIterationEndTime(iterationVO.getIterationEndTime());
            iteration.setIterationName(iterationVO.getIterationName());
            iteration.setIterationState(iterationVO.getIterationState());
            iterationRestService.updateIteration(iteration);
            return AjaxResponse.success(iterationVO);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }
}

