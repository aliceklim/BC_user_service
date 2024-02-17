package school.faang.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.dto.google_calendar.GoogleEventResponseDto;
import school.faang.user_service.google_calendar.GoogleCalendarService;
import school.faang.user_service.mapper.GoogleCalendarMapper;
import school.faang.user_service.service.event.EventService;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar")
@Slf4j
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;
    private final EventService eventService;
    private final GoogleCalendarMapper googleCalendarMapper;

    @Operation(summary = "Add event to google calendar")
    @PostMapping("/{id}")
    public GoogleEventResponseDto createCalendarEvent(@PathVariable("id") Long eventId) throws GeneralSecurityException, IOException {
        EventDto event = eventService.getEvent(eventId);
        return googleCalendarService.createEvent(googleCalendarMapper.toGoogleEventDto(event));
    }

//    @PostMapping("/{eventId}")
//    public String createEvent(@Valid @PathVariable Long eventId) throws GeneralSecurityException, IOException {
//        log.debug("Request for event creation. Event id: {}", eventId);
//        return googleCalendarService.createCalendarEvent(eventId);
//    }
//
//    @GetMapping("/auth/callback")
//    public void handleAuthorizationCallback(@RequestParam String state, @RequestParam String code
//                                            ) throws GeneralSecurityException, IOException {
//        String[] args = state.split("-");
//        Long userId = Long.parseLong(args[0]);
//        Long eventId = Long.parseLong(args[1]);
//        log.debug("Handled redirect request to create event for user with id: {}", userId);
//        googleCalendarService.getCredentialsFromCallback(code, eventId);
//    }
}