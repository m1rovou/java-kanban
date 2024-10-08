
import managers.*;
import tasks.*;
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();


        Task task1 = new Task("Задача 1", "Описание задачи 1", TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Задача 2", "Описание задачи 2", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);


        Epic epic1 = new Epic("Эпик 1", "Описание к эпик 1", TaskStatus.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание к эпик 2", TaskStatus.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);


        Subtask subtask1 = new Subtask("Подзадача 1 к эпику 1", "Описание к подзадаче 1", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2 к эпику 1", "Описание к подзадаче 2", TaskStatus.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3 к эпику 2", "Описание к подзадаче 3", TaskStatus.NEW, epic2.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        System.out.println("Задачи: ");
        taskManager.getTasks().forEach(System.out::println);

        System.out.println("\nЭпики: ");
        taskManager.getEpics().forEach(System.out::println);

        System.out.println("\nПодзадачи: ");
        taskManager.getSubtasks().forEach(System.out::println);

        task1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task1);
        task2.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task2);

        subtask1.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask1);

        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask2);

        subtask3.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask3);

        System.out.println("\nЗадачи после обновления статуса: ");
        taskManager.getTasks().forEach(System.out::println);

        System.out.println("\nЭпики после обновления статуса: ");
        taskManager.getEpics().forEach(System.out::println);

        System.out.println("\nПодзадачи после обновления статуса: ");
        taskManager.getSubtasks().forEach(System.out::println);

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic1.getId());

        System.out.println("\nЗадачи после удаления: ");
        taskManager.getTasks().forEach(System.out::println);

        System.out.println("\nЭпики после удаления: ");
        taskManager.getEpics().forEach(System.out::println);

        System.out.println("\nПодзадачи после удаления: ");
        taskManager.getSubtasks().forEach(System.out::println);

        taskManager.getTaskById(task2.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getEpicById(epic2.getId());

        printAllTasks(taskManager);

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("------------------------------------");
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllSubtaskByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
