package school.faang.user_service.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GoalCompletedEvent {
    private UUID completedGoalId;
}
