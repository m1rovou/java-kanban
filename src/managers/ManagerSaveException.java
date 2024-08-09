package managers;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ManagerSaveException saveException(Throwable cause) {
        return new ManagerSaveException("Error saving to file", cause);
    }

    public static ManagerSaveException loadExceptionException(Throwable cause) {
        return new ManagerSaveException("Error loading from file", cause);
    }
}
