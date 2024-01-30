package managers;
import my.directory.kanban.*;
import java.util.HashMap;
import java.util.List;

public class SomeTaskManager implements TaskManager {
    @Override
    public Task createTask(String title, String description, TaskStatus status) {
        return null;
    }

    @Override
    public Epic createEpic(String title, String description, TaskStatus status) {
        return null;
    }

    @Override
    public Subtask createSubtask(int epicId, String title, String description, TaskStatus status) {
        return null;
    }

    @Override
    public void updateTask(Task task) {
    }

    @Override
    public void updateEpic(Epic epic) {
    }

    @Override
    public void updateSubtask(Subtask subtask) {
    }

    @Override
    public Task getTaskById(int taskId) {
        return null;
    }

    @Override
    public Epic getEpicById(int epicId) {
        return null;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        return null;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId) {
        return null;
    }

    @Override
    public void deleteTaskById(int taskId) {
    }

    @Override
    public void deleteEpicById(int epicId) {
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
    }

    @Override
    public List<Task> getTasks() {
        return null;
    }

    @Override
    public List<Task> getEpics() {
        return null;
    }

    @Override
    public List<Task> getSubtasks() {
        return null;
    }

    @Override
    public List<Task> getEpicSubtasks(int epicId) {
        return null;
    }
    @Override
    public List<Task> getHistory() {
        return null;
    }
    @Override
    public HistoryManager getHistoryManager() {
        return null;
    }
}
