package school.faang.user_service.mapper;

import org.mapstruct.*;
import school.faang.user_service.dto.UserDto;import school.faang.user_service.entity.User;
import school.faang.user_service.mapper.GoalMapper;
import school.faang.user_service.mapper.SkillMapper;

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

    @Mapping(target = "followers", expression = "java(userDto.followerIds().stream().map(folId -> User.builder().id(folId).build()).toList())")
    @Mapping(target = "followees", expression = "java(userDto.followeeIds().stream().map(folId -> User.builder().id(folId).build()).toList())")
    @Mapping(target = "followers", source = "followerIds", qualifiedByName = "mapToUserList")
    @Mapping(target = "followees", source = "followeeIds", qualifiedByName = "mapToUserList")
    @Mapping(target = "mentors", expression = "java(userDto.mentors().stream().map(menId -> User.builder().id(menId).build()).toList())")
    @Mapping(target = "mentees", expression = "java(userDto.mentees().stream().map(menId -> User.builder().id(menId).build()).toList())")
    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> userList);
    List<User> toEntity(List<UserDto> userDtoList);

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