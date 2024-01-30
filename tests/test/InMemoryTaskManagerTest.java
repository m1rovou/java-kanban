package tests.test;

import my.directory.kanban.*;
import managers.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private final InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

    @Test
    void tasksWithSameIdAreEqual() {
        // Arrange
        Task task1 = new Task(1, "Задача 1", "Описание", TaskStatus.NEW);
        Task task2 = new Task(2, "Задача 1", "Описание", TaskStatus.NEW);

        // Act
        taskManager.createTask(task1.getTitle(), task1.getDescription(), task1.getStatus());
        Task retrievedTask = taskManager.getTaskById(task1.getId());

        // Assert
        assertNotEquals(task1, retrievedTask);
    }

    @Test
    void taskInheritorsWithSameIdAreEqual() {
        // Arrange
        Epic epic = taskManager.createEpic("Эпик 1", "Описание эпика", TaskStatus.IN_PROGRESS);
        Subtask subtask = taskManager.createSubtask(epic.getId(), "Подзадача", "Описание подзадачи", TaskStatus.DONE);

        // Act
        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask.getId());

        // Assert
        assertEquals(epic, retrievedEpic, "Эпики с одинаковым id должны быть равны");
        assertEquals(subtask, retrievedSubtask, "Подзадачи с одинаковым id должны быть равны");
    }


    @Test
    void noIdConflict() {
        // Arrange
        Task taskWithId = taskManager.createTask("Задача 1", "Описание для Задачи 1", TaskStatus.NEW);
        int explicitlyAssignedId = taskWithId.getId();

        Task taskWithGeneratedId = taskManager.createTask("Задача 2", "Описание для Задачи 2", TaskStatus.IN_PROGRESS);
        int generatedId = taskWithGeneratedId.getId();

        // Act
        Task retrievedTaskWithId = taskManager.getTaskById(explicitlyAssignedId);
        Task retrievedTaskWithGeneratedId = taskManager.getTaskById(generatedId);

        // Assert
        assertNotEquals(explicitlyAssignedId, generatedId, "Задачи имеют одинаковый id");
        assertNotNull(retrievedTaskWithId, "Задача с явно заданным id не найдена");
        assertNotNull(retrievedTaskWithGeneratedId, "Задача с сгенерированным id не найдена");
        assertEquals(taskWithId, retrievedTaskWithId, "Задачи с явно заданным id не совпадают");
        assertEquals(taskWithGeneratedId, retrievedTaskWithGeneratedId, "Задачи с сгенерированным id не совпадают");
    }

    @Test
    void addAndGetTasks() {
        // Arrange
        Task task = taskManager.createTask("Задача 1", "Описание для Задачи 1", TaskStatus.NEW);
        Epic epic = taskManager.createEpic("Эпик 1", "Описание для Эпика 1", TaskStatus.IN_PROGRESS);
        Subtask subtask = taskManager.createSubtask(epic.getId(), "Подзадача 1", "Описание для Подзадачи 1", TaskStatus.DONE);

        // Act
        Task retrievedTask = taskManager.getTaskById(task.getId());
        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask.getId());

        // Assert
        assertNotNull(retrievedTask, "Задача не найдена");
        assertEquals(task, retrievedTask, "Задачи не совпадают");

        assertNotNull(retrievedEpic, "Эпик не найден");
        assertEquals(epic, retrievedEpic, "Эпики не совпадают");

        assertNotNull(retrievedSubtask, "Подзадача не найдена");
        assertEquals(subtask, retrievedSubtask, "Подзадачи не совпадают");
    }
}
