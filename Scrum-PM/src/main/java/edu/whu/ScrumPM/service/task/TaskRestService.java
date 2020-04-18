package edu.whu.ScrumPM.service.task;


import edu.whu.ScrumPM.dao.Task.Task;
import edu.whu.ScrumPM.dao.teamProject.TeamProject;

import java.util.List;

public interface TaskRestService {

     Task saveTask(Task task);

     void deleteTask(Long taskId);

     void updateTask(Task task);

     Task getTask(Long taskId);

     List<Task> getTaskByIterationId(long iterationId);
}