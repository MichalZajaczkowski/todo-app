//package trainingUdemyProject.logic;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import trainingUdemyProject.TaskConfigurationProperties;
//import trainingUdemyProject.model.*;
//import trainingUdemyProject.model.projection.GroupReadModel;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class ProjectServiceTest {
//
//    @Test
//    @DisplayName("shout throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
//    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_openGroups_throwsIllegalStateException() {
//        // given
//        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
//        // and
//        TaskConfigurationProperties mocConfig = configurationReturning(false);
//
//        //system under test
//        var toTest = new ProjectService(null, mockGroupRepository, mocConfig);
//
////        //when + then
////        assertThatIllegalStateException()
////                .isThrownBy(() -> toTest.createGroup(LocalDateTime.now(),0));
////        //then
//        //when
//        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//        // then
//        assertThat(exception)
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("one undone group");
//    }
//
//    @Test
//    @DisplayName("shout throw IllegalArgumentException when configuration ok and no projects for a given id")
//    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentException() {
//        // given
//        var mockRepository = mock(ProjectRepository.class);
//        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
//        // and
//        TaskConfigurationProperties mocConfig = configurationReturning(true);
//
//        //system under test
//        var toTest = new ProjectService(mockRepository, null, mocConfig);
//
//        //when
//        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//        // then
//        assertThat(exception)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("id not found");
//    }
//
//    @Test
//    @DisplayName("shout throw IllegalArgumentException when  configured to allow just 1 group and no projects and projects for a given id")
//    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExists_noProjects_throwsIllegalArgumentException() {
//        // given
//        var mockRepository = mock(ProjectRepository.class);
//        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
//
//        // and
//        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
//        // and
//        TaskConfigurationProperties mocConfig = configurationReturning(true);
//
//        //system under test
//        var toTest = new ProjectService(mockRepository, mockGroupRepository, mocConfig);
//
//        //when
//        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//        // then
//        assertThat(exception)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("id not found");
//    }
//
//    @Test
//    @DisplayName("should create a new group from project")
//    void createGroup_configurationOk_existingProject_createsAndSaving() {
//        // given
//        var today = LocalDate.now().atStartOfDay();
//        // and
//        var project = projectWith("bar", Set.of(-1, -2));
//        var mockRepository = mock(ProjectRepository.class);
//        when(mockRepository.findById(anyInt()))
//                .thenReturn(Optional.of(project));
//        // and
//        inMemoryGroupRepository inMemoryGroupRepository = inMemoryGroupRepository();
//        int countBeforeCall = inMemoryGroupRepository.count();
//        // and
//        TaskConfigurationProperties mocConfig = configurationReturning(true);
//        //system under test
//        var toTest = new ProjectService(mockRepository, inMemoryGroupRepository, mocConfig);
//        //when
//        GroupReadModel result = toTest.createGroup(today, 1);
//        //then
//        assertThat(result.getDescription()).isEqualTo("bar");
////                .hasFieldOrPropertyWithValue("description", "bar");
//        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
//        assertThat(result.getTasks()).allMatch(task -> task.getDescription().equals("foo"));
//        assertThat(countBeforeCall + 1)
//                .isEqualTo(inMemoryGroupRepository.count());
//    }
//
//    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline) {
//        Set<ProjectStep> steps = daysToDeadline.stream()
//                .map(days -> {
//                    var step = mock(Project.class);
//                    when(step.getDescription()).thenReturn("foo");
//                    when(step.getDaysToDeadline()).thenReturn(days);
//                    return step;
//                }).collect(Collectors.toSet());
//        var result = mock(ProjectStep.class);
//        when(result.getDescription()).thenReturn(projectDescription);
//        when(result.getSteps()).thenReturn(steps);
//        return result;
//    }
//
//    private TaskGroupRepository groupRepositoryReturning(boolean result) {
//        var mockGroupRepository = mock(TaskGroupRepository.class);
//        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
//        return mockGroupRepository;
//    }
//
//    private TaskConfigurationProperties configurationReturning(boolean result) {
//        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
//        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
//        var mocConfig = mock(TaskConfigurationProperties.class);
//        when(mocConfig.getTemplate()).thenReturn(mockTemplate);
//        return mocConfig;
//    }
//
//    private inMemoryGroupRepository inMemoryGroupRepository() {
//        return new inMemoryGroupRepository();
//    }
//
//    private static class inMemoryGroupRepository implements TaskGroupRepository {
//
//        private int index = 0;
//        private Map<Integer, TaskGroup> map = new HashMap<>();
//
//        public int count() {
//            return map.values().size();
//        }
//
//        @Override
//        public List<TaskGroup> findAll() {
//            return new ArrayList<>(map.values());
//        }
//
//        @Override
//        public Optional<TaskGroup> findById(Integer id) {
//            return Optional.ofNullable(map.get(id));
//        }
//
//        @Override
//        public TaskGroup save(TaskGroup entity) {
//            if (entity.getId() == 0) {
//                try {
//                    var field = TaskGroup.class.getDeclaredField("id");
//                    field.setAccessible(true);
//                    field.set(entity, ++index);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            map.put(++index, entity);
//            return entity;
//        }
//
//        @Override
//        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
//            return map.values().stream()
//                    .filter(group -> !group.isDone())
//                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
//        }
//    }
//}
