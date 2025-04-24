package school.faang.user_service.exception;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;
import java.util.UUID;

@AllArgsConstructor
public class RequestNotFoundException extends RuntimeException {
    private UUID requesterId;
    private UUID receiverId;

    @Override
    public String getMessage() {
        return MessageFormat.format(
                "Mentorship for requesterId {0} and receiverId {1} not found", requesterId, receiverId);
    }
}
