package my.directory.kanban;
//
public class Task {
    private static int taskIdCounter = 0;

    private final int id;
    private final String title;
    private final String description;
    private TaskStatus status;

    public Task(String title, String description, TaskStatus status) {
        this.id = ++taskIdCounter;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
