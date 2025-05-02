package school.faang.user_service.controller.event;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.dto.event.EventFilterDto;
import school.faang.user_service.entity.event.Event;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.event.EventService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Slf4j
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Add event")
    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody @Valid EventDto event) {
        EventDto created = eventService.create(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get event by id")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable @NonNull UUID id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }
    @Operation(summary = "Get all events")
    @GetMapping
    public ResponseEntity<List<EventDto>> getEventsByFilter(@ModelAttribute @Valid EventFilterDto filter) {
        return ResponseEntity.ok(eventService.getEventsByFilter(filter));
    }

    @Operation(summary = "Update event")
    @PutMapping
    public ResponseEntity<Void> updateEvent(@RequestBody @Valid EventDto event) {
        eventService.updateEvent(event);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all created events by user id")
    @GetMapping("/users/{userId}/created")
    public ResponseEntity<List<EventDto>> getOwnedEvents(@PathVariable @NotNull UUID userId) {
        return ResponseEntity.ok(eventService.getOwnedEvents(userId));
    }

    @Operation(summary = "Get all events user is invited to")
    @GetMapping("/users/{userId}/participated")
    public ResponseEntity<List<EventDto>> getParticipatedEvents(@PathVariable @NotNull UUID userId) {
        return ResponseEntity.ok(eventService.getParticipatedEvents(userId));
    }

    @Operation(summary = "Delete event a user invited to by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable @NotNull UUID id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}