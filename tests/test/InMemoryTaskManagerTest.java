package test;

import org.junit.jupiter.api.Test;
import managers.InMemoryTaskManager;
import managers.TaskManager;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }

    @Test
    public void testUtilityClassAlwaysReturnsInitializedAndReadyManagers() {
        assertNotNull(taskManager, "Task manager should be initialized.");
        assertInstanceOf(TaskManager.class, taskManager);
    }

}