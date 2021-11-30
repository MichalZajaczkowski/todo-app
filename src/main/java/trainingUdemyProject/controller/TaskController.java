package trainingUdemyProject.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import trainingUdemyProject.model.TaskRepository;

@RepositoryRestController
public class TaskController {
    private static final Log LOG = LogFactory.getLog(TaskController.class);
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
//    @RequestMapping(method = RequestMethod.GET, path = "/tasks")
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})

    ResponseEntity<?> readAllTask() {
        LOG.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }
}