package school.faang.user_service.controller.event;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.dto.event.EventFilterDto;
import school.faang.user_service.entity.event.Event;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.event.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Slf4j
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Add event")
    @PostMapping
    public EventDto create(@RequestBody EventDto event) {
        if (checkValidation(event)) {
            throw new DataValidationException("Object is not valid");
        }
        return eventService.create(event);
    }

    public EventDto getEvent(long id) {
        validateId(id);
        return eventService.getEvent(id);
    }

    public void getEventsByFilter(EventFilterDto filter) {
        eventService.getEventsByFilter(filter);
    }

    public void updateEvent(EventDto event) {
        if (checkValidation(event)) {
            throw new DataValidationException("The event did not pass validation when updating the event");
        }
        eventService.updateEvent(event);
    }

    public void getOwnedEvents(long userId) {
        validateId(userId);
        eventService.getOwnedEvents(userId);
    }

    public List<Event> getParticipatedEvents(long userId) {
        validateId(userId);
        return eventService.getParticipatedEvents(userId);
    }

    public void deleteEvent(long id) {
        validateId(id);
        eventService.deleteEvent(id);
    }

    private boolean checkValidation(EventDto event) {
        return event.getTitle() == null && event.getTitle().isEmpty()
                && event.getStartDate() == null && event.getUserId() == null;
    }

    private void validateId(Long id) {
        if (id == null){
            throw new DataValidationException("Id is null");
        }
    }
}