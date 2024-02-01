import managers.*;
import my.directory.kanban.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {

    @Test
    void testAdd() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        for (int i = 0; i < 10; i++) {
            Task task = taskManager.createTask("Тест-задача-" + i, "Описание", TaskStatus.NEW);
            historyManager.add(task);
        }

        assertEquals(10, historyManager.getHistory().size());

        Task lastTask = taskManager.createTask("Тест-задача 10", "Описание", TaskStatus.NEW);
        historyManager.add(lastTask);

        assertEquals(10, historyManager.getHistory().size());

        Task historyTask = historyManager.getHistory().get(0);
        assertEquals("Тест-задача-1", historyTask.getTitle());
    }


    @Test
    void testGetHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        Task task = taskManager.createTask("Тест-задача", "Описание", TaskStatus.NEW);

        assertEquals(0, historyManager.getHistory().size());

        historyManager.add(task);

        assertEquals(1, historyManager.getHistory().size());

        Task historyTask = historyManager.getHistory().get(0);
        assertEquals("Тест-задача", historyTask.getTitle());
    }
}
