package tests.test;

import managers.HistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    void getDefaultReturnsInitializedManagers() {
        InMemoryTaskManager taskManager = (InMemoryTaskManager) Managers.getDefault();
        assertNotNull(taskManager, "TaskManager не должен быть null");

        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager не должен быть null");
    }
}
