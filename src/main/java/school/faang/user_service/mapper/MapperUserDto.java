package school.faang.user_service.mapper;

import org.mapstruct.*;
import school.faang.user_service.dto.user.CreatedUserDto;
import school.faang.user_service.dto.user.CreateUserRequestDto;
import school.faang.user_service.dto.user.UserDto;import school.faang.user_service.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {GoalMapper.class, SkillMapper.class},
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MapperUserDto {

    @Mapping(target = "followerIds", source = "followers", qualifiedByName = "mapToIdList")
    @Mapping(target = "followeeIds", source = "followees", qualifiedByName = "mapToIdList")
    @Mapping(target = "mentors", expression = "java(user.getMentors().stream().map(men -> men.getId()).toList())")
    @Mapping(target = "mentees", expression = "java(user.getMentees().stream().map(men -> men.getId()).toList())")
    UserDto toDto(User user);

    @Mapping(target = "followers", source = "followerIds", qualifiedByName = "mapToUserList")
    @Mapping(target = "followees", source = "followeeIds", qualifiedByName = "mapToUserList")
    @Mapping(target = "mentors", expression = "java(userDto.mentors().stream().map(menId -> User.builder().id(menId).build()).toList())")
    @Mapping(target = "mentees", expression = "java(userDto.mentees().stream().map(menId -> User.builder().id(menId).build()).toList())")
    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> userList);
    List<User> toEntity(List<UserDto> userDtoList);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "profilePicUrl", target = "profilePic")
    @Mapping(source = "id", target = "id")
    CreatedUserDto toCreatedUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", constant = "default")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "followees", ignore = true)
    @Mapping(target = "ownedEvents", ignore = true)
    @Mapping(target = "mentees", ignore = true)
    @Mapping(target = "mentors", ignore = true)
    @Mapping(target = "receivedMentorshipRequests", ignore = true)
    @Mapping(target = "sentMentorshipRequests", ignore = true)
    @Mapping(target = "sentGoalInvitations", ignore = true)
    @Mapping(target = "receivedGoalInvitations", ignore = true)
    @Mapping(target = "participatedEvents", ignore = true)
    @Mapping(target = "recommendationsGiven", ignore = true)
    @Mapping(target = "recommendationsReceived", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "contactPreference", ignore = true)
    @Mapping(target = "premium", ignore = true)
    User fromCreateRequest(CreateUserRequestDto dto);

    @Named("mapToIdList")
    default List<Long> mapToIdList(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Named("mapToUserList")
    default List<User> mapToUserList(List<Long> userIds) {
        return userIds.stream()
                .map(userId -> User.builder().id(userId).build())
                .collect(Collectors.toList());
    }
}