package school.faang.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.user.CreateUserRequestDto;
import school.faang.user_service.dto.user.CreatedUserDto;
import school.faang.user_service.entity.contact.PreferredContact;
import school.faang.user_service.mapper.MapperUserDto;
import school.faang.user_service.messaging.MessagePublisher;
import school.faang.user_service.messaging.events.ProfileViewEvent;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.filter.user.UserFilterDto;
import school.faang.user_service.filter.user.UserFilter;
import school.faang.user_service.entity.UserProfilePic;
import school.faang.user_service.profile_pic_generator.ProfilePicGenerator;
import school.faang.user_service.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final List<UserFilter> userFilters;
    private final MapperUserDto userMapper;
    private final MessagePublisher<ProfileViewEvent> profileViewEventMessagePublisher;
    private final UserContext userContext;
    private final ProfilePicGenerator profilePicGenerator;

    @Transactional
    public CreatedUserDto createUser (CreateUserRequestDto request) {
        User user = userMapper.fromCreateRequest(request);
        UserProfilePic pic = profilePicGenerator.generateProfilePic(user);
        user.setProfilePicUrl(pic.url());

        userRepository.save(user);

        return userMapper.toCreatedUserDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getPremiumUsers(UserFilterDto userFilterDto, Pageable pageable) {
        return applyFilter(userRepository.findPremiumUsers(), userFilterDto, pageable);
    }

    private Page<UserDto> applyFilter(Stream<User> userList, UserFilterDto userFilterDto, Pageable pageable) {
        List<User> filteredUsers = userFilters.stream()
                .filter(userFilter -> userFilter.isApplicable(userFilterDto))
                .flatMap(userFilter -> userFilter.apply(userList, userFilterDto))
                .toList();

        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int start = Math.min(page * pageSize, filteredUsers.size());
        int end = Math.min(start + pageSize, filteredUsers.size());
        List<UserDto> paginatedDtos = filteredUsers.subList(start, end).stream()
                .map(userMapper::toDto)
                .toList();

        return new PageImpl<>(paginatedDtos, pageable, filteredUsers.size());
    }

    @Transactional(readOnly = true)
    public UserDto getUser(UUID currentUserId , UUID userId) {
        String message = String.format("Entity with ID %s not found", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(message));
        profileViewEventMessagePublisher.publish(new ProfileViewEvent(currentUserId, userId,
                PreferredContact.EMAIL));
//        profileViewEventMessagePublisher.publish(new ProfileViewEvent(currentUserId, userId,
//                user.getContactPreference().getPreference()));

        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsersByIds(List<UUID> ids) {
        List<User> allById = userRepository.findAllById(ids);

        return allById.stream()
                .map(userMapper::toDto)
                .toList();
    }
}
