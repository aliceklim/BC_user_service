package school.faang.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.event.Event;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EventMapper {
    void update(@MappingTarget Event entity, EventDto updateEntity);

    @Mapping(target = "owner", source = "userId")
    @Mapping(target = "type", source = "eventType")
    @Mapping(target = "status", source = "eventStatus")
    Event toEvent(EventDto eventDto);

    @Mapping(target = "userId", source = "owner.id")
    @Mapping(target = "eventType", source = "type")
    @Mapping(target = "eventStatus", source = "status")
    EventDto toEventDto(Event event);

    default User mapToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}
