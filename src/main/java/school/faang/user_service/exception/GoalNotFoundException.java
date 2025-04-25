package school.faang.user_service.exception;

import java.text.MessageFormat;
import java.util.UUID;

public class GoalNotFoundException extends RuntimeException{
    private final UUID goalId;

    public GoalNotFoundException(UUID goalId){
        this.goalId = goalId;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Goal {0} not found", goalId);
    }
}
