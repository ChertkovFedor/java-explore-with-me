package ru.practicum.ewm.event.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.event.general.dto.update.UpdateEventRequest;
import ru.practicum.ewm.event.general.mapper.EventMapper;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.event.general.service.EventService;
import ru.practicum.ewm.util.enums.State;
import ru.practicum.ewm.util.enums.StateAction;
import ru.practicum.ewm.util.exception.InvalidEventDateException;
import ru.practicum.ewm.util.exception.InvalidOperationException;
import ru.practicum.ewm.util.validator.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.general.mapper.EventMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eRep;
    private final Validator valid;
    private final EventService eServ;

    @Override
    public List<EventDto> adminEventSearch(List<Long> users,
                                           List<State> states,
                                           List<Long> categories,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Long from,
                                           Integer size) {
        List<Event> foundEvents = eRep.adminEventSearch(users, states, categories, rangeStart, rangeEnd, from, size);
        List<EventDto> fullEventsDto = foundEvents.stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());

        Map<Long, Long> views = eServ.getStats(foundEvents, false);
        if (!views.isEmpty())
            fullEventsDto.forEach(e -> e.setViews(views.get(e.getId())));

        for (EventDto event : fullEventsDto) {
            Integer confirmedRequest = eRep.findConfirmedRequests(event.getId());
            if (confirmedRequest != null)
                event.setConfirmedRequests(confirmedRequest);
        }

        return fullEventsDto;
    }

    @Override
    public EventDto update(Long eventId, UpdateEventRequest updateEventAdminRequest) {
        Event eventToUpdate = valid.getEventIfExist(eventId);
        if (eventToUpdate.getEventDate().isBefore(LocalDateTime.now().plusHours(1)))
            throw new InvalidEventDateException("Invalid event date");
        if (!State.PENDING.equals(eventToUpdate.getState()))
            throw new InvalidOperationException(String.format("Invalid state: %s", eventToUpdate.getState().name()));
        Event updatedEvent = eServ.update(eventToUpdate, updateEventAdminRequest);
        StateAction updatedEventStateAction = updateEventAdminRequest.getStateAction();
        if (updatedEventStateAction == null)
            return toDto(eRep.save(updatedEvent));
        switch (updatedEventStateAction) {
            case PUBLISH_EVENT:
                updatedEvent.setPublishedOn(LocalDateTime.now());
                updatedEvent.setState(State.PUBLISHED);
                break;
            case REJECT_EVENT:
                updatedEvent.setState(State.CANCELED);
                break;
            default:
                throw new InvalidOperationException("Invalid state");
        }
        return toDto(eRep.save(updatedEvent));
    }
}
