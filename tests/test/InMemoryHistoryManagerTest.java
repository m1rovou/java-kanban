package tests.test;

import managers.InMemoryHistoryManager;
import my.directory.kanban.Task;
import my.directory.kanban.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager inMemoryHistoryManager;

    @BeforeEach
    void setUp() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldNotBeNullTaskList() {
        assertNotNull(inMemoryHistoryManager.getHistory());
    }

    @Test
    void shouldAddTask() {
        Task task1 = new Task("Задача1", "ИсходноеОписание", TaskStatus.NEW);
        inMemoryHistoryManager.add(task1);
        List<Task> list = inMemoryHistoryManager.getHistory();
        assertNotNull(list);
        assertEquals(1, list.size());
        Task firstTask = list.get(0);
        assertNotNull(firstTask);
        assertEquals("Задача1", firstTask.getName());
        assertEquals("ИсходноеОписание", firstTask.getDescription());
        assertEquals(TaskStatus.NEW, firstTask.getStatus());
        // Предполагается, что ID автоматически присваивается и увеличивается
        assertEquals(0, firstTask.getId()); // Обновите это значение, если ID задается по-другому
    }
}
