 import my.directory.kanban.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("Задача 1", "Описание для Задачи 1", TaskStatus.NEW);
        Task task2 = taskManager.createTask("Задача 2", "Описание для Задачи 2", TaskStatus.IN_PROGRESS);

        Epic epic1 = taskManager.createEpic("Эпик 1", "Описание для Эпика 1", TaskStatus.NEW);
        Epic epic2 = taskManager.createEpic("Эпик 2", "Описание для Эпика 2", TaskStatus.IN_PROGRESS);
        Epic epic3 = taskManager.createEpic("Эпик 3", "Описание для Эпика 3", TaskStatus.IN_PROGRESS);
        Epic epic4 = taskManager.createEpic("Эпик 4", "Описание для Эпика 4", TaskStatus.IN_PROGRESS);


        Subtask subtask1 = taskManager.createSubtask(epic1.getId(), "Подзадача 1", "Описание для Подзадачи 1", TaskStatus.DONE);
        Subtask subtask2 = taskManager.createSubtask(epic1.getId(), "Подзадача 2", "Описание для Подзадачи 2", TaskStatus.IN_PROGRESS);
        Subtask subtask3 = taskManager.createSubtask(epic2.getId(), "Подзадача 3", "Описание для Подзадачи 3", TaskStatus.DONE);
        Subtask subtask4 = taskManager.createSubtask(epic2.getId(), "Подзадача 4", "Описание для Подзадачи 4", TaskStatus.IN_PROGRESS);
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
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic1.getId()).values()) {
            System.out.println(subtask);
        }

        System.out.println("Подзадачи для Эпика 3:");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic3.getId()).values()) {
            System.out.println(subtask);
        }

        System.out.println("Подзадачи для Эпика 4:");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic4.getId()).values()) {
            System.out.println(subtask);
        }

        System.out.println("Подзадачи для Эпика 2:");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic2.getId()).values()) {
            System.out.println(subtask);
        }

        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getTaskById(task2.getId()));

        int subtaskIdToCheck = 8;
        Subtask retrievedSubtask = taskManager.getSubtaskById(subtaskIdToCheck);
        System.out.println("Подзадача " + retrievedSubtask + " найдена:");

        subtaskIdToCheck = 13;
        retrievedSubtask = taskManager.getSubtaskById(subtaskIdToCheck);
        System.out.println("Подзадача  " + retrievedSubtask + " найдена:");


        System.out.println("Вывод эпика 1,2,3,4:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println(taskManager.getEpicById(epic3.getId()));
        System.out.println(taskManager.getEpicById(epic4.getId()));
        task1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task1);
        System.out.println("Обновленная задача 1:");
        System.out.println(taskManager.getTaskById(task1.getId()));


        epic1.setStatus(TaskStatus.DONE);
        taskManager.updateEpic(epic1);
        System.out.println("Обновленный эпик 1:");
        System.out.println(taskManager.getEpicById(epic1.getId()));


        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        System.out.println("Обновленная подзадача 1:");
        System.out.println(taskManager.getSubtasksByEpicId(epic1.getId()).get(subtask1.getId()));


        taskManager.deleteTaskById(task2.getId());
        System.out.println("Удаление задачи 2. Все задачи после удаления:");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getTaskById(task2.getId()));


        taskManager.deleteEpicById(epic3.getId());
        System.out.println("Удаление эпика 3. Все эпики после удаления:");
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println(taskManager.getEpicById(epic3.getId()));
        System.out.println(taskManager.getEpicById(epic4.getId()));


        taskManager.deleteSubtaskById(subtask4.getId());
        System.out.println("Удаление подзадачи 4 из эпика 2. Подзадачи эпика 2 после удаления:");
        System.out.println(taskManager.getSubtasksByEpicId(epic2.getId()));
    }
}

