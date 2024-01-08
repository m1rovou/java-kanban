import java.util.HashMap;

public class TaskManager {
    private int taskIdCounter = 0;
    private int epicIdCounter = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, HashMap<Integer, Subtask>> subtasks = new HashMap<>();

    public Task createTask(String title, String description, TaskStatus status) {
        int taskId = ++taskIdCounter;
        Task newTask = new Task(taskId, title, description, status);
        tasks.put(taskId, newTask);
        return newTask;
    }

    public Epic createEpic(String title, String description, TaskStatus status) {
        int epicId = ++epicIdCounter;
        Epic newEpic = new Epic(epicId, title, description, status);
        epics.put(epicId, newEpic);


        recalculateEpicStatus(newEpic);

        return newEpic;
    }
    private void recalculateEpicStatus(Epic epic) {
        HashMap<Integer, Subtask> subtasksForEpic = getSubtasksByEpicId(epic.getId());

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


    public Subtask createSubtask(int epicId, String title, String description, TaskStatus status) {
        int subtaskId = ++taskIdCounter;
        Subtask newSubtask = new Subtask(subtaskId, epicId, title, description, status);

        if (subtasks.containsKey(epicId)) {
            subtasks.get(epicId).put(subtaskId, newSubtask);
        } else {
            HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
            subtaskHashMap.put(subtaskId, newSubtask);
            subtasks.put(epicId, subtaskHashMap);
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
            subtasks.get(epicId).put(subtask.getId(), subtask);


            Epic epic = getEpicById(epicId);
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

    public HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId) {
        if (subtasks.containsKey(epicId)) {
            return subtasks.get(epicId);
        } else {
            return new HashMap<>();
        }
    }
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpicById(int epicId) {
        epics.remove(epicId);
        subtasks.remove(epicId);
    }

    public void deleteSubtaskById(int subtaskId) {
        for (HashMap<Integer, Subtask> subtasks : subtasks.values()) {
            subtasks.remove(subtaskId);
        }
    }
}

