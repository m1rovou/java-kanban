package tests.test;

import managers.*;
import my.directory.kanban.TaskStatus;
import my.directory.kanban.Subtask;
import my.directory.kanban.Task;
import my.directory.kanban.Epic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class InMemoryTaskManagerTest {

    private InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
    }

    @Test
    public void shouldTasksBeConstantAfterAdd() {
        Task task1 = new Task("Задача1", "ИсходноеОписание1", TaskStatus.NEW);
        inMemoryTaskManager.addTask(task1);
        Task taskT1 = inMemoryTaskManager.getTask(0);
        assertEquals("Задача1", taskT1.getName());
        assertEquals("ИсходноеОписание1", taskT1.getDescription());
        assertEquals(TaskStatus.NEW, taskT1.getStatus());
        assertEquals(0, taskT1.getId());
    }

    @Test
    public void shouldHistoryManagerSaveFirstVersionOfTask() {
        Task task1 = new Task("Задача1", "ИсходноеОписание", TaskStatus.NEW);
        Task task2 = new Task("Задача1", "ИзмененноеОписание", TaskStatus.NEW);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.updateTask(task2);

        Task taskT1 = inMemoryTaskManager.getTask(0);
        List<Task> list = inMemoryTaskManager.getHistory();
        Task firstTask = list.get(0);
        assertEquals("ИзмененноеОписание", firstTask.getDescription());
    }
}