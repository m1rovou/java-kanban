package managers;
import my.directory.kanban.*;
import java.util.HashMap;
import java.util.List;

public interface    TaskManager {

    Task createTask(String title, String description, TaskStatus status);
    Epic createEpic(String title, String description, TaskStatus status);
    Subtask createSubtask(int epicId, String title, String description, TaskStatus status);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);
    Task getTaskById(int taskId);
    Epic getEpicById(int epicId);
    Subtask getSubtaskById(int subtaskId);
    HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId);
    void deleteTaskById(int taskId);
    void deleteEpicById(int epicId);
    void deleteSubtaskById(int subtaskId);
    public List<Task> getTasks();
    public List<Task> getEpics();
    List<Task> getSubtasks();
    public List<Task> getEpicSubtasks(int epicId);
    HistoryManager getHistoryManager();
    List<Task> getHistory();
}
