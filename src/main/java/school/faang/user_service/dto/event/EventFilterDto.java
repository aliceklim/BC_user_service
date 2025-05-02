package school.faang.user_service.dto.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;
import school.faang.user_service.dto.skill.SkillDto;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class EventFilterDto {
    private UUID id;

    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private UUID ownerId;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    private List<SkillDto> relatedSkills;

    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;

    @Min(value = 1, message = "Max attendees filter must be at least 1")
    private Integer maxAttendees;
}
