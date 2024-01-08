import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("Задача 1", "Описание для Задачи 1", TaskStatus.NEW);
        Task task2 = taskManager.createTask("Задача 2", "Описание для Задачи 2", TaskStatus.IN_PROGRESS);

        Epic epic1 = taskManager.createEpic("Эпик 1", "Описание для Эпика 1", TaskStatus.NEW);

        Subtask subtask1 = taskManager.createSubtask(epic1.getId(), "Подзадача 1", "Описание для Подзадачи 1", TaskStatus.DONE);
        Subtask subtask2 = taskManager.createSubtask(epic1.getId(), "Подзадача 2", "Описание для Подзадачи 2", TaskStatus.IN_PROGRESS);

        Epic epic2 = taskManager.createEpic("Эпик 2", "Описание для Эпика 2", TaskStatus.IN_PROGRESS);

        Subtask subtask3 = taskManager.createSubtask(epic2.getId(), "Подзадача 3", "Описание для Подзадачи 3", TaskStatus.DONE);
        Subtask subtask4 = taskManager.createSubtask(epic2.getId(), "Подзадача 4", "Описание для Подзадачи 4", TaskStatus.IN_PROGRESS);

        Epic epic3 = taskManager.createEpic("Эпик 3", "Описание для Эпика 3", TaskStatus.IN_PROGRESS);
        Epic epic4 = taskManager.createEpic("Эпик 4", "Описание для Эпика 4", TaskStatus.IN_PROGRESS);

        Subtask subtask5 = taskManager.createSubtask(epic3.getId(), "Подзадача 5", "Описание для Подзадачи 5", TaskStatus.DONE);
        Subtask subtask6 = taskManager.createSubtask(epic3.getId(), "Подзадача 6", "Описание для Подзадачи 6", TaskStatus.IN_PROGRESS);
        Subtask subtask7 = taskManager.createSubtask(epic4.getId(), "Подзадача 7", "Описание для Подзадачи 7", TaskStatus.DONE);
        Subtask subtask8 = taskManager.createSubtask(epic4.getId(), "Подзадача 8", "Описание для Подзадачи 8", TaskStatus.IN_PROGRESS);


        System.out.println("Все задачи:");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getTaskById(task2.getId()));


        System.out.println("Вывод эпика 1,2,3,4:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println(taskManager.getEpicById(epic3.getId()));
        System.out.println(taskManager.getEpicById(epic4.getId()));


        System.out.println("Подзадачи для Эпика 1:");
        HashMap<Integer, Subtask> subtasksForEpic1 = taskManager.getSubtasksByEpicId(epic1.getId());
        ArrayList<Subtask> subtasksListForEpic1 = new ArrayList<>(subtasksForEpic1.values());
        for (Subtask subtask : subtasksListForEpic1) {
            System.out.println(subtask);
        }
        System.out.println("Подзадачи для Эпика 3:");
        HashMap<Integer, Subtask> subtasksForEpic3 = taskManager.getSubtasksByEpicId(epic3.getId());
        ArrayList<Subtask> subtasksListForEpic3 = new ArrayList<>(subtasksForEpic3.values());
        for (Subtask subtask : subtasksListForEpic3) {
            System.out.println(subtask);
        }
        System.out.println("Подзадачи для Эпика 4:");
        HashMap<Integer, Subtask> subtasksForEpic4 = taskManager.getSubtasksByEpicId(epic4.getId());
        ArrayList<Subtask> subtasksListForEpic4 = new ArrayList<>(subtasksForEpic4.values());
        for (Subtask subtask : subtasksListForEpic4) {
            System.out.println(subtask);
        }

        System.out.println("Подзадачи для Эпика 2:");
        HashMap<Integer, Subtask> subtasksForEpic2 = taskManager.getSubtasksByEpicId(epic2.getId());
        ArrayList<Subtask> subtasksListForEpic2 = new ArrayList<>(subtasksForEpic2.values());
        for (Subtask subtask : subtasksListForEpic2) {
            System.out.println(subtask);
        }
    }
}

