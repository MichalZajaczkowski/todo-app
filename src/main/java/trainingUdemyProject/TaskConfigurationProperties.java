package trainingUdemyProject;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    private boolean allowMultipleTasksFromTemplates;

    boolean isAllowMultipleTasksFromTemplates() {
        return allowMultipleTasksFromTemplates;
    }

    void setAllowMultipleTasksFromTemplates(boolean allowMultipleTasksFromTemplates) {
        this.allowMultipleTasksFromTemplates = allowMultipleTasksFromTemplates;
    }
}
