package school.faang.user_service.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.faang.user_service.dto.event.EventDto;
import school.faang.user_service.dto.event.EventFilterDto;
import school.faang.user_service.dto.skill.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.event.Event;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.EventMapper;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.event.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final List<EventFilter> eventFilters;

    @Transactional
    public EventDto create(EventDto eventDto) {
        UUID userId = eventDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataValidationException("User {} not found", userId));
        if (!(userHasRequiredSkills(eventDto, user))) {
                throw new DataValidationException("Creator's skills {} doesn't correspond to the event's {} necessary skills",
                        eventDto.getId(), user.getId());
        }
        Event newEvent = eventMapper.toEvent(eventDto);
        Event savedEvent = eventRepository.save(newEvent);

        return eventMapper.toEventDto(savedEvent);
    }

    public EventDto getEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataValidationException("Event {} not found}", eventId));
        return eventMapper.toEventDto(event);
    }

    public List<EventDto> getEventsByFilter(EventFilterDto filters) {
        Stream<Event> events = eventRepository.findAll().stream();

        for (EventFilter filter : eventFilters) {
            if (filter.isApplicable(filters)) {
                events = filter.apply(events, filters);
            }
        }

        return events.map(eventMapper::toEventDto).toList();
    }

    public EventDto updateEvent(EventDto newEvent) {
        Event oldEvent = eventRepository.findById(newEvent.getId())
                .orElseThrow(() -> new DataValidationException(
                        "Event {} does not exist", newEvent.getId()));
        eventMapper.update(oldEvent, newEvent);
        return eventMapper.toEventDto(eventRepository.save(oldEvent));
    }

    public List<EventDto> getOwnedEvents(UUID userId) {
        return eventMapper.toEventDtoList(eventRepository.findAllByUserId(userId));
    }

    public List<EventDto> getParticipatedEvents(UUID userId) {
        return eventMapper.toEventDtoList(eventRepository.findParticipatedEventsByUserId(userId));
    }

    public void deleteEvent(UUID eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new DataValidationException("Event {} does not exist", eventId);
        }
        eventRepository.deleteById(eventId);
    }

    private boolean userHasRequiredSkills(EventDto eventDto, User user) {
        Set<String> userSkillTitles = user.getSkills().stream()
                .map(Skill::getTitle)
                .collect(Collectors.toSet());

        Set<String> requiredSkillTitles = eventDto.getRelatedSkills().stream()
                .map(SkillDto::getTitle)
                .collect(Collectors.toSet());

        return userSkillTitles.containsAll(requiredSkillTitles);
    }
}
