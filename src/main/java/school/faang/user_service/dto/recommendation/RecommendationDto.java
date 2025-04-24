package school.faang.user_service.dto.recommendation;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class RecommendationDto {

    private UUID id;
    private UUID authorId;
    private UUID receiverId;
    @NotEmpty
    private String content;
    private List<SkillOfferDto> skillOffers;
    private LocalDateTime createdAt;
}
