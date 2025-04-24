package school.faang.user_service.dto.recommendation;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;
import school.faang.user_service.entity.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RecommendationRequestDto {
    private UUID id;
    private String message;
    private RequestStatus status;
    private List<UUID> skillIds;
    private UUID requesterId;
    private UUID receiverId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
