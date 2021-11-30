package trainingUdemyProject.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import trainingUdemyProject.model.TaskRepository;

@RepositoryRestController
public class TaskController {
    private static final Log LOG = LogFactory.getLog(TaskController.class);
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    ResponseEntity<?> readAllTask() {
        LOG.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }
}
