package school.faang.user_service.dto.recommendation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SkillOfferDto {

    private UUID id;
    private UUID skillId;
}
