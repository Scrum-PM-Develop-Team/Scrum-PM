package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.Task.Task;
import edu.whu.ScrumPM.dao.teamProject.TeamProject;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.model.IterationVO;
import edu.whu.ScrumPM.model.TaskVO;
import edu.whu.ScrumPM.service.task.TaskRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rest")
public class TaskRestController {
    @Resource(name= "taskRestJPAServiceImpl")
    TaskRestService taskRestService;

    @PostMapping("/task")
    public @ResponseBody
    AjaxResponse saveTask(@RequestBody IterationVO iterationVO) {

        try {
            TaskVO taskVO=iterationVO.getTaskVOs().get(0);
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
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @DeleteMapping("/task/{id}")
    public @ResponseBody AjaxResponse deleteIteration(@PathVariable Long id){
        try {
            taskRestService.deleteTask(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

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
