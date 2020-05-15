package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.Task.Task;
import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.model.IterationVO;
import edu.whu.ScrumPM.model.TaskVO;
import edu.whu.ScrumPM.service.iteration.IterationRestService;
import edu.whu.ScrumPM.service.task.TaskRestService;
import edu.whu.ScrumPM.service.user.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rest")
public class TaskRestController {
    @Resource(name= "taskRestJPAServiceImpl")
    TaskRestService taskRestService;

    @Resource(name= "iterationJPARestServiceImpl")
    IterationRestService iterationRestService;

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @CrossOrigin
    @PostMapping("/task")
    public @ResponseBody
    AjaxResponse saveTask(@RequestBody IterationVO iterationVO) {
        TaskVO taskVO=iterationVO.getTaskVOs().get(0);
        try {
            iterationRestService.getIteration(iterationVO.getIterationId());
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的迭代");
        }
        try {
            userRestService.getUserByUserId(iterationVO.getTaskVOs().get(0).getTaskExecutor().getUserId());
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的用户");
        }
        Task task=Task.builder()
                .iterationId(iterationVO.getIterationId())
                .taskBeginTime(taskVO.getTaskBeginTime())
                .taskEndTime(taskVO.getTaskEndTime())
                .taskPriority(taskVO.getTaskPriority())
                .taskRemark(taskVO.getTaskRemark())
                .taskState(taskVO.getTaskState())
                .endTime(taskVO.getEndTime())
                .taskName(taskVO.getTaskName())
                .userId(taskVO.getTaskExecutor().getUserId())
                .build();
        taskVO.setTaskId(taskRestService.saveTask(task).getTaskId());
        return AjaxResponse.success(taskVO);
    }

    @CrossOrigin
    @DeleteMapping("/task/{id}")
    public @ResponseBody AjaxResponse deleteIteration(@PathVariable Long id){
        try {
            taskRestService.deleteTask(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.failMes("没有此id对应的任务");
        }
    }

    @CrossOrigin
    @PutMapping("/task")
    public @ResponseBody AjaxResponse updateTask(@RequestBody TaskVO taskVO){
        try{
            Task task=taskRestService.getTask(taskVO.getTaskId());
            task.setEndTime(taskVO.getEndTime());
            task.setTaskBeginTime(taskVO.getTaskBeginTime());
            task.setTaskEndTime(taskVO.getTaskEndTime());
            task.setTaskPriority(taskVO.getTaskPriority());
            task.setTaskRemark(taskVO.getTaskRemark());
            task.setTaskState(taskVO.getTaskState());
            task.setTaskName(taskVO.getTaskName());
            task.setUserId(taskVO.getTaskExecutor().getUserId());
            taskRestService.updateTask(task);
            return AjaxResponse.success(taskVO);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

}
