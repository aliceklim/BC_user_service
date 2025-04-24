package school.faang.user_service.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class MentorshipStartEvent {
    private UUID mentorId;
    private UUID menteeId;
}
