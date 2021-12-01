package trainingUdemyProject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingUdemyProject.model.Task;
import trainingUdemyProject.model.TaskRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
//    @RequestMapping(method = RequestMethod.GET, path = "/tasks")
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTask() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }
    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> readAllTask(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(page).getContent());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@RequestBody @Valid Task toUpdate){
        taskRepository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
}
