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
import javafx.beans.binding.LongExpression;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @CrossOrigin
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

                List<UserVO> userVOS=new ArrayList<UserVO>();
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

    @CrossOrigin
    @GetMapping("/teamRank")
    public @ResponseBody AjaxResponse getTeamRank(){
        List<IterationVO> iterationVOS=new ArrayList<IterationVO>();
        Map<String, Double> map = new HashMap<String, Double>();
        List<TeamProject> teamProjects= teamProjectRestService.getAll();
        for (TeamProject teamProject:teamProjects){
            double allTask=0;
            double completeTask=0;
            List<Iteration> iterationsForTeamProjectId=
                    iterationRestService.getIterationByTeamProjectId(teamProject.getTeamProjectId());
            for (Iteration iterationForTeamProjectId:iterationsForTeamProjectId){
                try {
                    List<Task> tasksForIterationId=taskRestService.getTaskByIterationId(iterationForTeamProjectId.getIterationId());
                    allTask+=tasksForIterationId.size();
                    for (Task taskForIterationId:tasksForIterationId){
                        if (taskForIterationId.getTaskState().equals("已完成")){
                            completeTask+=1;
                        }
                    }

                }catch (Exception e){
                    continue;
                }

            }
            if (allTask==0){
                map.put(teamProject.getTeamProjectName(),allTask);
            }else {
                map.put(teamProject.getTeamProjectName(),completeTask/allTask);
            }

        }
        return AjaxResponse.success(map);
    }

    @GetMapping("/taskAssigned")
    public @ResponseBody AjaxResponse getTasksAssignedByPerformer(@RequestParam Long teamProjectId){
        List<Iteration> iterationsForTeamProjectId=
                iterationRestService.getIterationByTeamProjectId(teamProjectId);
        List<Long> userIds=new ArrayList<Long>();
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        Map<String,Integer> userMap=new HashMap<String, Integer>();
        for (Iteration iterationForTeamProjectId:iterationsForTeamProjectId){
            List<Task> tasksForIterationId;
            try{
                tasksForIterationId=taskRestService.getTaskByIterationId(iterationForTeamProjectId.getIterationId());
            }catch (Exception e){
                continue;
            }
            for (Task taskForIterationId:tasksForIterationId){
                if (!map.containsKey(taskForIterationId.getUserId())){
                    map.put(taskForIterationId.getUserId(),1);
                }else {
                    int i= map.get(taskForIterationId.getUserId());
                    i+=1;
                    map.remove(taskForIterationId.getUserId());
                    map.put(taskForIterationId.getUserId(),i);
                }
            }
        }
        for (Long key : map.keySet()) {
            Integer value = map.get(key);
            User user=userRestService.getUserByUserId(key);
            userMap.put(user.getUserName(),value);
        }
        return AjaxResponse.success(userMap);
    }

    @PutMapping("/task")
    public  @ResponseBody AjaxResponse putTask(@RequestBody TaskVO taskVO){
        Task task=taskRestService.getTask(taskVO.getTaskId());
        if(taskVO.getEndTime()!=null){
            task.setEndTime(taskVO.getEndTime());
        }
        if(taskVO.getTaskBeginTime()!=null){
            task.setTaskBeginTime(taskVO.getTaskBeginTime());
        }
        if(taskVO.getTaskEndTime()!=null){
            task.setTaskEndTime(taskVO.getTaskEndTime());
        }
        if(taskVO.getTaskExecutor()!=null){
            task.setUserId(taskVO.getTaskExecutor().getUserId());
        }
        if(taskVO.getTaskName()!=null&&taskVO.getTaskName()!=""){
            task.setTaskName(taskVO.getTaskName());
        }
        if(taskVO.getTaskPriority()!=null&&taskVO.getTaskPriority()!=""){
            task.setTaskPriority(taskVO.getTaskPriority());
        }
        if(taskVO.getTaskRemark()!=null&&taskVO.getTaskRemark()!=""){
            task.setTaskRemark(taskVO.getTaskRemark());
        }
        if(taskVO.getTaskState()!=null&&taskVO.getTaskState()!=""){
            task.setEndTime(taskVO.getEndTime());
        }

        taskRestService.updateTask(task);
        return AjaxResponse.success(task);
    }

    @GetMapping("/taskComplete")
    public @ResponseBody AjaxResponse getTasksCompleteByteamProjectId(@RequestParam Long teamProjectId){
        List<Iteration> iterationsForTeamProjectId=
                iterationRestService.getIterationByTeamProjectId(teamProjectId);
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        Map<Long, Integer> mapComplete = new HashMap<Long, Integer>();
        for (Iteration iterationForTeamProjectId:iterationsForTeamProjectId){
            List<Task> tasksForIterationId;
            try{
                tasksForIterationId=taskRestService.getTaskByIterationId(iterationForTeamProjectId.getIterationId());
            }catch (Exception e){
                continue;
            }
            for (Task taskForIterationId:tasksForIterationId){
                if (!map.containsKey(taskForIterationId.getUserId())){
                    map.put(taskForIterationId.getUserId(),0);
                    mapComplete.put(taskForIterationId.getUserId(),0);
                }else {
                    if (taskForIterationId.getTaskState().equals("已完成")){
                        int i= map.get(taskForIterationId.getUserId());
                        i+=1;
                        map.remove(taskForIterationId.getUserId());
                        map.put(taskForIterationId.getUserId(),i);
                        int j= mapComplete.get(taskForIterationId.getUserId());
                        j+=1;
                        mapComplete.remove(taskForIterationId.getUserId());
                        mapComplete.put(taskForIterationId.getUserId(),i);
                    }else {
                        int i= map.get(taskForIterationId.getUserId());
                        i+=1;
                        map.remove(taskForIterationId.getUserId());
                        map.put(taskForIterationId.getUserId(),i);
                    }

                }
            }
        }
        Map<String, Double> finalMap = new HashMap<String, Double>();
        for (Long key : map.keySet()) {
            Integer value = map.get(key);
            Integer value2 = mapComplete.get(key);
            User user=userRestService.getUserByUserId(key);
            if (value==0){
                finalMap.put(user.getUserName(),0.0);
            }else {
                finalMap.put(user.getUserName(),(double)value2/(double)value);
            }
        }
        return AjaxResponse.success(finalMap);
    }

    @GetMapping("/taskStay")
    public @ResponseBody AjaxResponse getTasksStayPyteamProjectId(@RequestParam Long teamProjectId){
        List<Iteration> iterationsForTeamProjectId=
                iterationRestService.getIterationByTeamProjectId(teamProjectId);
        int[] data03={0,0,0,0};
        int[] data39={0,0,0,0};
        int[] data9_={0,0,0,0};
        for (Iteration iterationForTeamProjectId:iterationsForTeamProjectId){
            List<Task> tasksForIterationId;
            try{
                tasksForIterationId=taskRestService.getTaskByIterationId(iterationForTeamProjectId.getIterationId());
            }catch (Exception e){
                continue;
            }
            for (Task taskForIterationId:tasksForIterationId){
                int d= differentDays(taskForIterationId.getTaskBeginTime(),new Date());
                if (d<3){
                    if (taskForIterationId.getTaskState().equals(("准备中"))){
                        data03[0]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("开发中")))
                    {
                        data03[1]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("测试中"))){
                        data03[2]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("已完成"))){
                        data03[3]+=1;
                    }
                }
                else if(d>=3&&d<9){
                    if (taskForIterationId.getTaskState().equals(("准备中"))){
                        data39[0]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("开发中")))
                    {
                        data39[1]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("测试中"))){
                        data39[2]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("已完成"))){
                        data39[3]+=1;
                    }
                }
                else if(d>9){
                    if (taskForIterationId.getTaskState().equals(("准备中"))){
                        data9_[0]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("开发中")))
                    {
                        data9_[1]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("测试中"))){
                        data9_[2]+=1;
                    }
                    else if(taskForIterationId.getTaskState().equals(("已完成"))){
                        data9_[3]+=1;
                    }
                }
            }
        }

        return AjaxResponse.success(new int[][]{data03,data39,data9_});
    }

    @GetMapping("/getAllUser")
    public @ResponseBody AjaxResponse getAll(){
        return AjaxResponse.success(userRestService.getAll());
    }

    public static int differentDays(Date date1,Date date2){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);

        if (year1 != year2)  //不同年
        {
            int timeDistance = 0;
            for (int i = year1 ; i < year2 ;i++){ //闰年
                if (i%4==0 && i%100!=0||i%400==0){
                    timeDistance += 366;
                }else { // 不是闰年
                    timeDistance += 365;
                }
            }
            return  timeDistance + (day2-day1);
        }else{// 同年
            return day2-day1;
        }

    }
}
