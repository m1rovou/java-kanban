package managers;

import java.io.File;

public class Managers {
    private static HistoryManager historyManager;

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File("C:\\Users\\Comp\\java-kanban\\resources\\task.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

