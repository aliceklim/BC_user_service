package school.faang.user_service.mapper;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.mapstruct.*;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.dto.google_calendar.GoogleEventDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoogleCalendarMapper {
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "mapDate")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "mapDate")
    @Mapping(source = "title", target = "summary")
    default GoogleEventDto toGoogleEventDto(EventDto eventDto) {
        return null;
    }

    @Mapping(source = "startDate", target = "start")
    @Mapping(source = "endDate", target = "end")
    Event toGoogleEvent(GoogleEventDto eventDto);

    @Named("mapDate")
    default EventDateTime mapDate(LocalDateTime date) {
        DateTime dateTime = new DateTime(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));

        EventDateTime eventDateTime = new EventDateTime();
        eventDateTime.setDateTime(dateTime);

        return eventDateTime;
    }
}