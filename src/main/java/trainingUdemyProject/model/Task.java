package trainingUdemyProject.model;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Tables;

@Entity
@Tables(name = "tasks")
public class Task {
    private int id;
    private String description;
    private boolean done;
}
