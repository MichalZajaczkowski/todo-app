package trainingUdemyProject.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingUdemyProject.model.Project;
import trainingUdemyProject.model.ProjectRepository;
import trainingUdemyProject.model.TaskGroup;
import trainingUdemyProject.model.TaskGroupRepository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();

}
