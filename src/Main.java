import my.directory.kanban.Epic;
import my.directory.kanban.TaskStatus;
import my.directory.kanban.Subtask;
import managers.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Эпик 1", "Описание Эпик 1");
        Subtask subtask1 = new Subtask("Сабтаск 1", "Задача 1", TaskStatus.NEW, 0);
        Subtask subtask2 = new Subtask("Сабтаск 2", "Задача 2", TaskStatus.NEW, 0);
        Subtask subtask3 = new Subtask("Сабтаск 3", "Задача 3", TaskStatus.NEW, 0);
        Epic emptyEpic = new Epic("---", "---");

        inMemoryTaskManager.addTask(epic);
        inMemoryTaskManager.addTask(subtask1);
        inMemoryTaskManager.addTask(subtask2);
        inMemoryTaskManager.addTask(subtask3);
        inMemoryTaskManager.addTask(emptyEpic);

        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getEpic(0));
        System.out.println(inMemoryTaskManager.getSubtask(1));
        System.out.println(inMemoryTaskManager.getSubtask(2));
        System.out.println(inMemoryTaskManager.getSubtask(3));
        System.out.println(inMemoryTaskManager.getEpic(4));
        System.out.println("-".repeat(60));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println("-".repeat(60));
        System.out.println(inMemoryTaskManager.getSubtask(2));
        System.out.println(inMemoryTaskManager.getSubtask(3));
        System.out.println(inMemoryTaskManager.getSubtask(1));
        System.out.println(inMemoryTaskManager.getEpic(4));
        System.out.println(inMemoryTaskManager.getEpic(0));
        System.out.println("-".repeat(60));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println("-".repeat(60));
        inMemoryTaskManager.removeEpic(4);
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println("-".repeat(60));
        inMemoryTaskManager.removeEpic(0);
        System.out.println(inMemoryTaskManager.getHistory());
    }
}