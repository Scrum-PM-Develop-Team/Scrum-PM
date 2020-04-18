package edu.whu.ScrumPM.service.task;



import edu.whu.ScrumPM.dao.Task.Task;
import edu.whu.ScrumPM.dao.Task.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TaskRestJPAServiceImpl implements TaskRestService {

    @Resource
    private TaskRepository taskRepository;

    @Override
    public Task saveTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    @Override
    public List<Task> getTaskByIterationId(long iterationId) {
        return taskRepository.getTasksByIterationId(iterationId);
    }
}
