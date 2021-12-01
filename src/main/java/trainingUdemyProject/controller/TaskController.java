package trainingUdemyProject.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import trainingUdemyProject.model.Task;
import trainingUdemyProject.model.SqlTaskRepository;

import java.util.List;

@RestController
public class TaskController {
    private static final Log LOG = LogFactory.getLog(TaskController.class);
    private final SqlTaskRepository taskRepository;

    TaskController(SqlTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
//    @RequestMapping(method = RequestMethod.GET, path = "/tasks")
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTask() {
        LOG.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }
    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> readAllTask(Pageable pageable) {
        LOG.warn("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }
}
