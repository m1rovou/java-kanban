package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    protected abstract T createTaskManager() throws IOException;

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = createTaskManager();
    }

    @Test
    public void testAddTask() {
        Task task = new Task("Task 1", "Description", TaskStatus.NEW);
        taskManager.createTask(task);
        assertNotNull(taskManager.getTaskById(task.getId()));
    }

    @Test
    public void testAddSubtask() {
        Epic epic = new Epic("Epic 1", "Description", TaskStatus.NEW);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description", TaskStatus.NEW, Duration.ofHours(1), LocalDateTime.now(), epic.getId());
        taskManager.createSubtask(subtask);
        assertNotNull(taskManager.getSubtaskById(subtask.getId()));
        assertEquals(epic.getId(), subtask.getEpicId());
    }

    @Test
    public void testAddEpic() {
        Epic epic = new Epic("Epic 1", "Description", TaskStatus.NEW);
        taskManager.createEpic(epic);
        assertNotNull(taskManager.getEpicById(epic.getId()));
    }

    @Test
    public void testUpdateTaskInTaskManager() {
        Task task1 = new Task("Task 1", "Descr 1", TaskStatus.NEW);
        taskManager.createTask(task1);

        task1.setDescription("Update Description");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task1);

        Task updateTask = taskManager.getTaskById(task1.getId());
        assertNotNull(updateTask);
        assertEquals("Update Description",task1.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS,updateTask.getStatus());
    }

    @Test
    public void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Descr 1", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("Task 2", "Descr 2", TaskStatus.IN_PROGRESS);
        task2.setId(1);
        assertEquals(task1,task2,"Задачи с одинаковым идентификатором должны быть равными.");
    }

    @Test
    public void testSubtaskEqualityById() {
        Subtask subtask1 = new Subtask("Subtask 1", "Descr 1", TaskStatus.NEW, 1);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Subtask 2", "Descr 2", TaskStatus.IN_PROGRESS, 1);
        subtask2.setId(1);

        assertEquals(subtask1,subtask2,"Подзадачи с одинаковым идентификатором должны быть равными.");

    }

    @Test
    public void testEpicEqualityById() {
        Epic epic1 = new Epic("Epic 1", "Descr epic 1", TaskStatus.NEW);
        epic1.setId(1);
        Epic epic2 = new Epic("Epic 2", "Descr epic 2", TaskStatus.IN_PROGRESS);
        epic2.setId(1);
        assertEquals(epic1,epic2,"Эпики с одинаковым идентификатором должны быть равными.");
    }

    @Test
    public void testInMemoryTaskManagerAddsAndFindsTasksById() {
        Task task1 = new Task("Task 1","Descr 1",TaskStatus.NEW);
        taskManager.createTask(task1);

        assertNotNull(taskManager.getTaskById(task1.getId()),"Adds tasks of different types and can find them by id.");
    }

    @Test
    public void testTasksWithGeneratedAndSpecifiedIdsDoNotConflict() {
        Task specifiedId = new Task("Task 1","Descr 1", TaskStatus.NEW);
        taskManager.createTask(specifiedId);
        specifiedId.setId(1);

        Task generateID = new Task("Task 2", "Descr 2", TaskStatus.NEW);
        taskManager.createTask(generateID);

        assertNotEquals(specifiedId.getId(),generateID.getId());
        assertEquals(2,taskManager.getTasks().size(),"2 tasks.");
    }

    @Test
    public void testTaskImmutabilityWhenAddingATaskToTheManager() {
        Task task1 = new Task("Task 1", "Descr 1", TaskStatus.NEW);
        taskManager.createTask(task1);
        Task retrievedTask = taskManager.getTaskById(task1.getId());

        assertEquals(task1,retrievedTask,"Task should be immutable when added to manager.");
    }

    @Test
    public void testDeletedSubtasksShouldNotStoreOldIdsInsideThem() {
        Epic epic1 = new Epic("Epic 1", "Descr epic 1", TaskStatus.NEW);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Descr 1", TaskStatus.NEW, epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.deleteSubtaskById(subtask1.getId());
        Subtask removeSubtask = taskManager.getSubtaskById(subtask1.getId());
        assertNull(removeSubtask,"Subtask 1 remove and = null");
        List<Subtask> epicSubtask = taskManager.getAllSubtaskByEpicId(epic1.getId());
        assertFalse(epicSubtask.contains(subtask1),"Subtask should be remove");

    }

    @Test
    public void testThereShouldBeNoIrrelevantIdSubtasksInsideEpics() {
        Epic epic1 = new Epic("Epic 1", "Descr epic 1", TaskStatus.NEW);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Descr 1", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Descr 2", TaskStatus.NEW, epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        taskManager.deleteSubtaskById(subtask1.getId());

        List<Subtask> epicSubtask = taskManager.getAllSubtaskByEpicId(epic1.getId());
        for (Subtask subtask : epicSubtask) {
            assertNotEquals(subtask1.getId(), subtask.getId(), "The remote ID must not be present in the list");
        }
    }

    @Test
    public void testChangingAnyFieldUsingSettersAndCheckingForErrorsAfterChanges() {
        Task task1 = new Task("Task 1", "Descr 1", TaskStatus.NEW);
        taskManager.createTask(task1);
        task1.setTitle("New Title Task 1");
        task1.setId(3);
        task1.setDescription("New Description Task 1");
        task1.setStatus(TaskStatus.IN_PROGRESS);

        Epic epic1 = new Epic("Epic 1", "Descr epic 1", TaskStatus.NEW);
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Descr 1", TaskStatus.NEW, epic1.getId());
        taskManager.createSubtask(subtask1);
        subtask1.setId(4);

        epic1.setId(2);
        epic1.setDescription("New Description epic 1");
        epic1.setStatus(TaskStatus.IN_PROGRESS);
        epic1.setTitle("New Title Epic 1");
        taskManager.updateEpic(epic1);

        assertEquals("Subtask 1", subtask1.getTitle(),"It should be title 'Subtask 1'");
        assertEquals("Descr 1", subtask1.getDescription(), "It should be description 'Descr 1'");
        assertEquals(TaskStatus.NEW, subtask1.getStatus(), "It should be status 'NEW'");
        assertEquals(4, subtask1.getId(), "It should be Id 4");

        assertEquals("New Title Task 1", task1.getTitle(),"It should be title 'New Title Task 1' ");
        assertEquals("New Description Task 1", task1.getDescription(), "It should be descr 'New Description Task 1'");
        assertEquals(TaskStatus.IN_PROGRESS, task1.getStatus(), "It should be 'IN_PROGRESS'");
        assertEquals(3, task1.getId(), "Should be Id 3");
    }

    @Test
    public void testEpicStatusWithDifferentSubtaskStatuses() {
        Epic epic1 = new Epic("Epic 1", "Descr epic 1", TaskStatus.NEW);
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Descr 1", TaskStatus.NEW, Duration.ofHours(1), LocalDateTime.now(), epic1.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Descr 2", TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.now().plusDays(1), epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(TaskStatus.NEW, taskManager.getEpicById(epic1.getId()).getStatus());

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        epic1.updateEpicFields();

        assertEquals(TaskStatus.DONE, taskManager.getEpicById(epic1.getId()).getStatus());

        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        epic1.updateEpicFields();

        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicById(epic1.getId()).getStatus());

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        epic1.updateEpicFields();

        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpicById(epic1.getId()).getStatus());
    }

    @Test
    public void testNoOverlapBetweenTasks() {
        Task task1 = new Task("Task 1", "Description", TaskStatus.NEW, Duration.ofHours(1), LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task2 = new Task("Task 2", "Description", TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 11, 0));
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        List<Task> tasks = taskManager.getTasks();

        assertFalse(tasks.get(0).getEndTime().isAfter(task2.getStartTime()));
    }



}