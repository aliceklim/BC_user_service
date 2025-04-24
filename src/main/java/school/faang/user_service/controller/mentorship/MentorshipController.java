package school.faang.user_service.controller.mentorship;

import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.service.MentorshipService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MentorshipController {
    private final MentorshipService mentorshipService;

    public List<UserDto> getMentees(UUID userId) {
        return mentorshipService.getMentees(userId);
    }

    public List<UserDto> getMentors(UUID userId) {
        return mentorshipService.getMentors(userId);
    }

    public void deleteMentee(UUID menteeId, UUID mentorId) {
        mentorshipService.deleteMentee(menteeId, mentorId);
    }

    public void deleteMentor(UUID mentorId, UUID menteeId) {
        mentorshipService.deleteMentor(mentorId, menteeId);
    }
}
