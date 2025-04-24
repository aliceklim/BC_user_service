package school.faang.user_service.dto.skill;

import lombok.*;

import java.util.UUID;

@Builder
@Data
public class SkillDto {
    private UUID id;
    private final String title;
}
