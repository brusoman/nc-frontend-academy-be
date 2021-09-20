package nc.backend.controllers;

import nc.backend.dtos.TaskDto;
import nc.backend.dtos.TaskListDto;
import nc.backend.services.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId){
        return this.taskService.getTask(taskId);
    }

    @GetMapping("/all")
    public TaskListDto getAllTasks(){
        return taskService.getAllTasks();
    }
}