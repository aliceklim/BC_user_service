package school.faang.user_service.exception;

import java.util.UUID;

public class DataValidationException extends RuntimeException {
    public DataValidationException(String message) {
        super(message);
    }
    public DataValidationException(String message, UUID userId) {
        super(message);
    }

    public DataValidationException(String message, UUID eventId, UUID userId) {
        super(message);
    }
}
