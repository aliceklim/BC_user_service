package school.faang.user_service.exception;

public class EventSerializationException extends RuntimeException{
    public EventSerializationException(String className) {
        super("Failed to serialize GoalCompletedEvent for class: " + className);
    }

    public EventSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
