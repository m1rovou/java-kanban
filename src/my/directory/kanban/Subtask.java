package my.directory.kanban;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(int epicId, String title, String description, TaskStatus status) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", epicId=" + epicId +
                '}';
    }
}

