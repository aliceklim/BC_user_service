package school.faang.user_service.dto;

import lombok.Builder;
import school.faang.user_service.dto.goal.GoalDto;
import school.faang.user_service.dto.skill.SkillDto;

import java.util.List;

@Builder
public record UserDto(Long id,
                      String username,
                      String email,
                      String phone,
                      String aboutMe,
                      boolean active,
                      String city,
                      Integer experience,
                      List<Long> followerIds,
                      List<Long> followeeIds,
                      List<Long> mentors,
                      List<Long> mentees,
                      CountryDto country,
                      List<GoalDto> goals,
                      List<SkillDto> skills,
                      PreferredContact preference) {
    public enum PreferredContact {
        EMAIL, SMS, TELEGRAM
    }
}
