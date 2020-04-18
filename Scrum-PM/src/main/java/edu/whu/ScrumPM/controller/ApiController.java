package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.Task.Task;
import edu.whu.ScrumPM.dao.iteration.Iteration;
import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.dao.teamRelationship.TeamRelationship;
import edu.whu.ScrumPM.dao.user.User;
import edu.whu.ScrumPM.model.*;
import edu.whu.ScrumPM.service.iteration.IterationRestService;
import edu.whu.ScrumPM.service.task.TaskRestService;
import edu.whu.ScrumPM.service.teamProject.TeamProjectRestService;
import edu.whu.ScrumPM.service.teamRelationship.TeamRelationshipRestService;
import edu.whu.ScrumPM.service.user.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @Resource(name= "teamRelationshipRestJPAServiceImpl")
    TeamRelationshipRestService teamRelationshipRestService;

    @Resource(name= "iterationJPARestServiceImpl")
    IterationRestService iterationRestService;

    @Resource(name= "taskRestJPAServiceImpl")
    TaskRestService taskRestService;

    @Resource(name= "teamProjectRestJPAServiceImpl")
    TeamProjectRestService teamProjectRestService;

    @GetMapping( "/userNameForAll")
    public @ResponseBody AjaxResponse getAllByUserName(@RequestParam String userName){
        try{
            List<TeamProjectVO> teamProjectVOS=new ArrayList<TeamProjectVO>();
            User user=userRestService.getUser(userName);
            List<TeamRelationship> teamRelationshipsForUserId= teamRelationshipRestService.getTeamRelationshipByUserId(user.getUserId());
            for (TeamRelationship teamRelationshipForUserId:teamRelationshipsForUserId){
                TeamProjectVO tempTeamProjectVO=new TeamProjectVO();
                TeamProject teamProject= teamProjectRestService.getTeamProject(teamRelationshipForUserId.getTeamProjectId());
                tempTeamProjectVO.setTeamProjectId(teamProject.getTeamProjectId());
                tempTeamProjectVO.setTeamProjectName(teamProject.getTeamProjectName());

                List<UserVO> userVOS=new ArrayList<UserVO>();;
                List<TeamRelationship> teamRelationshipsForTeamProjectId=
                        teamRelationshipRestService.getTeamRelationshipByTeamProjectId(teamProject.getTeamProjectId());
                for (TeamRelationship teamRelationshipForProjectId:teamRelationshipsForTeamProjectId){
                    User tempUser= userRestService.getUserByUserId(teamRelationshipForProjectId.getUserId());
                    UserVO tempUserVO=new UserVO();
                    tempUserVO.setUserId(tempUser.getUserId());
                    tempUserVO.setUserName(tempUser.getUserName());
                    tempUserVO.setUserState(teamRelationshipForProjectId.getUserState());
                    userVOS.add(tempUserVO);
                }
                tempTeamProjectVO.setUserVOs(userVOS);

                List<IterationVO> iterationVOS=new ArrayList<IterationVO>();
                List<Iteration> iterationsForTeamProjectId=
                        iterationRestService.getIterationByTeamProjectId(teamProject.getTeamProjectId());
                for (Iteration iterationForTeamProjectId:iterationsForTeamProjectId){
                    IterationVO tempIterationVO=new IterationVO();
                    tempIterationVO.setIterationId(iterationForTeamProjectId.getIterationId());
                    tempIterationVO.setIterationBeginTime(iterationForTeamProjectId.getIterationBeginTime());
                    tempIterationVO.setIterationEndTime(iterationForTeamProjectId.getIterationEndTime());
                    tempIterationVO.setIterationName(iterationForTeamProjectId.getIterationName());
                    tempIterationVO.setIterationState(iterationForTeamProjectId.getIterationState());

                    List<TaskVO> taskVOS=new ArrayList<TaskVO>();
                    List<Task> tasksForIterationId=taskRestService.getTaskByIterationId(iterationForTeamProjectId.getIterationId());
                    for (Task taskForIterationId:tasksForIterationId){
                        TaskVO tempTaskVO=new TaskVO();
                        tempTaskVO.setEndTime(taskForIterationId.getEndTime());
                        tempTaskVO.setTaskBeginTime(taskForIterationId.getTaskBeginTime());
                        tempTaskVO.setTaskEndTime(taskForIterationId.getTaskEndTime());

                        User tempUser= userRestService.getUserByUserId(taskForIterationId.getUserId());
                        UserVO tempUserVO=new UserVO();
                        tempUserVO.setUserName(tempUser.getUserName());
                        tempUserVO.setUserId(tempUser.getUserId());
                        tempTaskVO.setTaskExecutor(tempUserVO);

                        tempTaskVO.setTaskPriority(taskForIterationId.getTaskPriority());
                        tempTaskVO.setTaskRemark(taskForIterationId.getTaskRemark());
                        tempTaskVO.setTaskState(taskForIterationId.getTaskState());
                        tempTaskVO.setTaskName(taskForIterationId.getTaskName());
                        tempTaskVO.setTaskId(taskForIterationId.getTaskId());
                        taskVOS.add(tempTaskVO);
                    }
                    tempIterationVO.setTaskVOs(taskVOS);
                    iterationVOS.add(tempIterationVO);
                }
                tempTeamProjectVO.setIterationVOs(iterationVOS);
                teamProjectVOS.add(tempTeamProjectVO);
            }
            return AjaxResponse.success(teamProjectVOS);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }
}