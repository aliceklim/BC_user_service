package school.faang.user_service.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.goal.GoalStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GoalDto {
    private UUID id;
    @NotNull(message = "Title cannot be null")
    @Size(min = 3, message = "Title must have at least 3 letters")
    private String title;
    private String description;
    private Long parentId;
    private GoalStatus status;
    private List<UUID> skillIds;
    private List<UUID> userIds;
    private List<String> skills;
}