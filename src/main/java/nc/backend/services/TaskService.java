package nc.backend.services;

import nc.backend.daos.TaskDao;
import nc.backend.dtos.TaskDto;
import nc.backend.dtos.TaskListDto;
import nc.backend.entities.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private TaskDao taskDao;
    private static Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    public TaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public TaskDto getTask(Long taskId) {
        Task task = taskDao.findByID(taskId);
        return buildTaskDtoFromTask(task);
    }
  
    public TaskListDto getAllTasks(){
        List<Task> tasks = taskDao.findAllTasks();
        logger.info("-----Tasks are sent-----");
        return buildTaskDtoListFromTaskList(tasks);
    }

    public TaskDto buildTaskDtoFromTask(Task task){
            if (task == null) {
                return null;
            }

        TaskDto taskDto = new TaskDto();
        taskDto.setAttempts_max(task.getAttempts_max());
        taskDto.setDescription(task.getDescription());
        taskDto.setName(task.getTask_name());
        taskDto.setDeadline(task.getDeadline().toString());
        taskDto.setUrlSample(task.getFile_path());
        return taskDto;
    }

    private TaskDto buildTaskDtoFromTaskForList(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getTask_name());
        taskDto.setSection(task.getSection());
        return taskDto;
    }

    private TaskListDto buildTaskDtoListFromTaskList(List<Task> tasks){
        List<TaskDto> taskDtoList = new ArrayList<>();
        tasks.forEach(task -> taskDtoList.add(buildTaskDtoFromTaskForList(task)));
        return new TaskListDto(taskDtoList);
    }
}
