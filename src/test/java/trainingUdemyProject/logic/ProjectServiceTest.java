package trainingUdemyProject.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import trainingUdemyProject.TaskConfigurationProperties;
import trainingUdemyProject.model.TaskGroupRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("shout throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_openGroups_throwsIllegalStateException() {
        // given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        // and
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);
        // and
        var mocConfig = mock(TaskConfigurationProperties.class);
        when(mocConfig.getTemplate()).thenReturn(mockTemplate);

        //system under test
        var toTest = new ProjectService(null, mockGroupRepository, mocConfig);

//        //when + then
//        assertThatIllegalStateException()
//                .isThrownBy(() -> toTest.createGroup(LocalDateTime.now(),0));
//        //then
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(),0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }
}