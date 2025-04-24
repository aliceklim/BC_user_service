package school.faang.user_service.dto.goal;


import lombok.Data;
import java.util.UUID;
import school.faang.user_service.entity.RequestStatus;

@Data
public class GoalInvitationDto {
    private UUID id;
    private UUID inviterId;
    private UUID invitedUserId;
    private UUID goalId;
    private RequestStatus status;
}
