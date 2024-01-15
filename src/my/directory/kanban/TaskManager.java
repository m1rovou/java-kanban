package my.directory.kanban;
//
import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private static int taskIdCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Task createTask(String title, String description, TaskStatus status) {
        int taskId = ++taskIdCounter;
        Task newTask = new Task(title, description, status);
        tasks.put(taskId, newTask);
        return newTask;
    }

    public Epic createEpic(String title, String description, TaskStatus status) {
        int epicId = ++taskIdCounter;
        Epic newEpic = new Epic(title, description, status);
        epics.put(epicId, newEpic);

        recalculateEpicStatus(newEpic);

        return newEpic;
    }

    public Subtask createSubtask(int epicId, String title, String description, TaskStatus status) {
        int subtaskId = ++taskIdCounter;
        Subtask newSubtask = new Subtask(epicId, title, description, status);
        subtasks.put(subtaskId, newSubtask);

        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtask(newSubtask);
            recalculateEpicStatus(epic);
        }

        return newSubtask;
    }




    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }


    public void updateSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (subtasks.containsKey(epicId)) {
            subtasks.put(subtask.getId(), subtask);

            Epic epic = epics.get(epicId);
            if (epic != null) {
                recalculateEpicStatus(epic);
            }
        }
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }
    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }
    public HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId) {
        HashMap<Integer, Subtask> subtasksForEpic = new HashMap<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksForEpic.put(subtask.getId(), subtask);
            }
        }
        return subtasksForEpic;
    }
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpicById(int epicId) {
        epics.remove(epicId);
        subtasks.remove(epicId);
    }

    public void deleteSubtaskById(int subtaskId) {
        subtasks.remove(subtaskId);
    }

    private void recalculateEpicStatus(Epic epic) {
        Map<Integer, Subtask> subtasksForEpic = getSubtasksByEpicId(epic.getId());

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtasksForEpic.values()) {
            TaskStatus status = subtask.getStatus();
            if (status != TaskStatus.NEW) {
                allNew = false;
            }
            if (status != TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (subtasksForEpic.isEmpty() || allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
