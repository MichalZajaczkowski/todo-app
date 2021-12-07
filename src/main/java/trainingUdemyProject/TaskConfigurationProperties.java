package trainingUdemyProject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    private boolean allowMultipleTasksFromTemplates;

    public boolean isAllowMultipleTasksFromTemplates() {
        return allowMultipleTasksFromTemplates;
    }

    public void setAllowMultipleTasksFromTemplates(boolean allowMultipleTasksFromTemplates) {
        this.allowMultipleTasksFromTemplates = allowMultipleTasksFromTemplates;
    }
}
