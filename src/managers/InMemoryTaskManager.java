package managers;

import my.directory.kanban.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int taskIdCounter = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private static final int HISTORY_SIZE = 10;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public Task createTask(String title, String description, TaskStatus status) {
        int taskId = ++taskIdCounter;
        Task newTask = new Task(taskId, title, description, status);
        tasks.put(taskId, newTask);
        return newTask;
    }

    @Override
    public Epic createEpic(String title, String description, TaskStatus status) {
        int epicId = ++taskIdCounter;
        Epic newEpic = new Epic(epicId, title, description, status);
        epics.put(epicId, newEpic);

        recalculateEpicStatus(newEpic);

        return newEpic;
    }
    @Override
    public Subtask createSubtask(int epicId, String title, String description, TaskStatus status) {
        int subtaskId = ++taskIdCounter;
        Subtask newSubtask = new Subtask(subtaskId, epicId, title, description, status);
        subtasks.put(subtaskId, newSubtask);

        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtask(newSubtask);
            recalculateEpicStatus(epic);
        }

        return newSubtask;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
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

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }
    @Override
    public HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId) {
        HashMap<Integer, Subtask> subtasksForEpic = new HashMap<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksForEpic.put(subtask.getId(), subtask);
            }
        }
        return subtasksForEpic;
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void deleteEpicById(int epicId) {
        Epic epicToRemove = epics.remove(epicId);
        if (epicToRemove != null) {
            HashMap<Integer, Subtask> subtasksForEpic = getSubtasksByEpicId(epicId);
            subtasks.values().removeAll(subtasksForEpic.values());
        }
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        subtasks.remove(subtaskId);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Task> getEpicSubtasks(int epicId) {
        List<Task> epicSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epicSubtasks.addAll(epic.getSubtasks());
        }
        return epicSubtasks;
    }

    @Override
    public List<Task> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }
    private void recalculateEpicStatus(Epic epic) {
        if (epic != null) {
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
    }

}
