package trainingUdemyProject.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import trainingUdemyProject.TaskConfigurationProperties;
import trainingUdemyProject.model.Task;
import trainingUdemyProject.model.TaskGroup;
import trainingUdemyProject.model.TaskGroupRepository;
import trainingUdemyProject.model.TaskRepository;
import trainingUdemyProject.model.projection.GroupReadModel;
import trainingUdemyProject.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope()
public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;
    private TaskConfigurationProperties config;



    TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());

        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return  repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
    }
}
