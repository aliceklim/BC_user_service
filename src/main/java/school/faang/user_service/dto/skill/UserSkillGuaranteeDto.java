package school.faang.user_service.dto.skill;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSkillGuaranteeDto {

    private UUID id;
    private UUID userId;
    private UUID skillId;
    private UUID guarantorId;
}
