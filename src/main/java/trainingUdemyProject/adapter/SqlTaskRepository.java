package trainingUdemyProject.adapter;


import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingUdemyProject.model.Task;
import trainingUdemyProject.model.TaskRepository;


@Repository
public interface SqlTaskRepository extends TaskRepository,  JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id =:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
}
