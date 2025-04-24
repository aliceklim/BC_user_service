package school.faang.user_service.dto.user;

import lombok.Builder;
import java.util.UUID;
import school.faang.user_service.dto.CountryDto;
import school.faang.user_service.dto.goal.GoalDto;
import school.faang.user_service.dto.skill.SkillDto;

import java.util.List;

@Builder
public record UserDto(UUID id,
                      String name,
                      String surname,
                      String email,
                      String phone,
                      String aboutMe,
                      boolean active,
                      String city,
                      Integer experience,
                      List<UUID> followerIds,
                      List<UUID> followeeIds,
                      List<UUID> mentors,
                      List<UUID> mentees,
                      CountryDto country,
                      List<GoalDto> goals,
                      List<SkillDto> skills,
                      PreferredContact preference) {
    public enum PreferredContact {
        EMAIL, SMS, TELEGRAM
    }
}
