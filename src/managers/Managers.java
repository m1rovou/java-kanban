package managers;
public class Managers {
    public static TaskManager getDefault() {
        boolean someManagers = false;
        boolean anyManagers = false;

        if (someManagers) {
            return new SomeTaskManager();
        } else if (anyManagers) {
            return new AnyTaskManager();
        } else {
            HistoryManager historyManager = getDefaultHistory();
            return new InMemoryTaskManager(historyManager);
        }
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
