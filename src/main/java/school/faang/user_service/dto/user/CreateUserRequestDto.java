package school.faang.user_service.dto.user;
import jakarta.validation.constraints.*;
import lombok.Builder;
import school.faang.user_service.dto.CountryDto;
import school.faang.user_service.dto.goal.GoalDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.contact.PreferredContact;

import java.util.List;

@Builder
public record CreateUserRequestDto(@NotBlank String username,
                                   @NotBlank String name,
                                   @NotBlank String surname,
                                   @Email @NotBlank String email,
                                   @Pattern(regexp = "^[+]{1}(?:[0-9\\-()\\/\\.\\s]?){6,15}[0-9]{1}$") String phone,
                                   @Size(max = 500) String aboutMe,
                                   @NotBlank String city,
                                   @Min(0) @Max(5) Integer experience,
                                   CountryDto country,
                                   List<GoalDto> goals,
                                   List<SkillDto> skills,
                                   PreferredContact preference) {
}
