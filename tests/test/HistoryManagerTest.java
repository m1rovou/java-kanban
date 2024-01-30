package tests.test;

import managers.*;
import my.directory.kanban.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {

    @Test
    void testHistoryManagerSavesPreviousVersions() {

        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);


        Task task = taskManager.createTask("Тест-задача", "Описание", TaskStatus.NEW);

        if (!historyManager.getHistory().isEmpty()) {
            Task previousVersion = historyManager.getHistory().get(0);
            assertEquals(TaskStatus.NEW, previousVersion.getStatus());
            assertEquals("Тест-задача", previousVersion.getTitle());
            assertEquals("Описание", previousVersion.getDescription());
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);

        if (!historyManager.getHistory().isEmpty()) {
            Task previousVersion = historyManager.getHistory().get(0);
            assertEquals(TaskStatus.NEW, previousVersion.getStatus());
            assertEquals("Тест-задача", previousVersion.getTitle());
            assertEquals("Описание", previousVersion.getDescription());
        }

        task.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task);

        if (!historyManager.getHistory().isEmpty()) {
            Task previousVersion = historyManager.getHistory().get(0);
            assertEquals(TaskStatus.IN_PROGRESS, previousVersion.getStatus());
            assertEquals("Тест-задача", previousVersion.getTitle());
            assertEquals("Описание", previousVersion.getDescription());
        }
    }
}