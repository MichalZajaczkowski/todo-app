package trainingUdemyProject.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
//@RepositoryRestResource(path = "todos", collectionResourceRel = "todos")
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Override
    @RestResource(exported = false)
    default void deleteById(Integer integer) {

    }
    @Override
    @RestResource(exported = false)
    default void delete(Task entity) {
    }
    @RestResource(path = "done", rel = "done")
    List<Task> findByDone(@Param("state") boolean done);
}
